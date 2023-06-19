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
import org.python.util.PythonInterpreter;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {
    private final String repositorio;
    private boolean check;

    @DataBoundConstructor
    public HelloWorldBuilder(String repositorio) {
        this.repositorio = repositorio;
    }
    public String getRepositorio() {
        return repositorio;
    }

    public boolean getCheck(){ return check;}

    @DataBoundSetter
    public void checkbox(boolean check) {
        this.check = check;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("String do repositorio: " + repositorio);
        listener.getLogger().println("Valor check: " + check);
        try {
            // Caminho para o executável Python
            String pythonExecutable = "python";

            // Caminho para o arquivo Python a ser executado
            String pythonScript = "C:\\Users\\user\\Desktop\\plugin\\FAST\\src\\main\\resources\\hello.py";

            // Parâmetros do script Python
            String param1 = "valor1";

            // Comando completo a ser executado
            String command = pythonExecutable + " " + pythonScript + " " + param1;

            // Criação do processo
            ProcessBuilder processBuilder = new ProcessBuilder(command.split(" "));
            Process process = processBuilder.start();

            // Leitura da saída do processo
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                listener.getLogger().println(line);
            }

            // Aguarda a finalização do processo
            int exitCode = process.waitFor();

        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }

    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0){
                return FormValidation.error("Insira o parametro 1");
            }
            /*if (!value.matches("((http|git|ssh|http(s)|file|\\/?)|"
                    + "(git@[\\w\\.]+))(:(\\/\\/)?)"
                    + "([\\w\\.@\\:/\\-~]+)(\\.git)(\\/)?")) {
                return FormValidation.warning("Endereço git errado. Insira novamente");
            }*/
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
