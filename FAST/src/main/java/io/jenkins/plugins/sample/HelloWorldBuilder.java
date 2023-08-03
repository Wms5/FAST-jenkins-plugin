package io.jenkins.plugins.sample;

import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Builder;
import hudson.util.FormValidation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.servlet.ServletException;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.DataBoundSetter;
import org.kohsuke.stapler.QueryParameter;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String name;
    private final String algorithm;
    private boolean useFrench;

    @DataBoundConstructor
    public HelloWorldBuilder(String name,String algorithm ) {
        this.name = name;
        this.algorithm = algorithm;
    }

    public String getName() {
        return name;
    }
    public String getAlgorithm() {
        return algorithm;
    }

    public boolean isUseFrench() {
        return useFrench;
    }

    @DataBoundSetter
    public void setUseFrench(boolean useFrench) {
        this.useFrench = useFrench;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener)
            throws InterruptedException, IOException {
        listener.getLogger().println("String do repositorio: " + name);
        listener.getLogger().println("String do algorimto: " + this.algorithm);
        try {
            // Caminho para o executável Python
            String pythonExecutable = "python";
            String pythonScript = "C:\\Users\\user\\Desktop\\PacoteReplicacao\\maven-FAST-update\\py\\prioritize.py";
            String command = pythonExecutable + " " + pythonScript + " " + name + " "+"FAST-pw";
            listener.getLogger().println("comando:" + command);
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

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter boolean useFrench,@QueryParameter String algorithm)
                throws IOException, ServletException {
            if (value.length() == 0){
                return FormValidation.error("O campo de Name não pode ser vazio.");
            }

            if (algorithm == null || algorithm.trim().isEmpty()) {
                return FormValidation.error("O campo de algorithm não pode ser vazio.");
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
