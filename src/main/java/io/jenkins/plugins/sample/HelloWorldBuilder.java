package io.jenkins.plugins.sample;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

import org.eclipse.jgit.api.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private boolean useFrench;

    private final String repositoryUrl = "https://github.com/DinoSaulo/maven-FAST.git";
    private final String destinationFolder = "src/main/resources/fast";

    @DataBoundConstructor
    public HelloWorldBuilder(String name,String algorithm ) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }

    public static void cloneRepository(String repositoryUrl, String destinationFolder) {
        try {
            CloneCommand cloneCommand = Git.cloneRepository()
                    .setURI(repositoryUrl)
                    .setDirectory(new File(destinationFolder));
            Git git = cloneCommand.call();
            git.close();

            System.out.println("Repositório clonado com sucesso em: " + destinationFolder);

        } catch (Exception e) {
            System.err.println("Erro ao clonar o repositório: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void executePipInstall(String requirementsFilePath) {
        try {
            Path path = Paths.get(requirementsFilePath);
            String absolutePath = path.toAbsolutePath().toString();

            ProcessBuilder processBuilder = new ProcessBuilder("pip3 install -r " + absolutePath);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("Instalação concluída com sucesso.");
            } else {
                System.err.println("Erro ao executar o comando pip install. Código de saída: " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Erro ao executar o comando pip install: " + e.getMessage());
        }
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        listener.getLogger().println("Picked algorithm: " + name);
        listener.getLogger().println("WORKSPACE path: " + workspace.absolutize());

        try {
            cloneRepository(this.repositoryUrl,this.destinationFolder);
            executePipInstall("/home/wilkinson/Desktop/TG/FAST/src/main/resources/fast/requirements.txt");
            String pythonExecutable = "python3";
            String pythonScript = System.getProperty("user.dir")+"/src/main/resources/fast/py/prioritize.py";
            String command = pythonExecutable + " " + pythonScript + " " + workspace.absolutize() + " "+  name;
            listener.getLogger().println("command:" + command);

            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                listener.getLogger().println(line);
            }
            process.waitFor();
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench)
                throws IOException, ServletException {
            if (value.isEmpty()){
                return FormValidation.error("Choose among the following options: FAST-pw, FAST-one, FAST-log, FAST-sqrt and FAST-all.");
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "FAST";
        }
    }
}
