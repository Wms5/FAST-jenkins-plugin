package io.jenkins.plugins.sample;

import br.ufba.jnose.core.Config;
import br.ufba.jnose.dto.TestClass;
import br.ufba.jnose.dto.TestSmell;
import hudson.Launcher;
import hudson.EnvVars;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import java.util.List;

import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;
import br.ufba.jnose.core.JNoseCore;

public class HelloWorldBuilder extends Builder implements SimpleBuildStep {

    private final String repositorio;
    private boolean check;

    private Config config;

    private void setConfig(){
        Config conf = new Config() {
            @Override
            public Boolean assertionRoulette() {
                return true;
            }


            @Override
            public Boolean conditionalTestLogic() {
                return true;
            }

            @Override
            public Boolean constructorInitialization() {
                return true;
            }

            @Override
            public Boolean defaultTest() {
                return true;
            }

            @Override
            public Boolean dependentTest() {
                return true;
            }

            @Override
            public Boolean duplicateAssert() {
                return true;
            }

            @Override
            public Boolean eagerTest() {
                return true;
            }

            @Override
            public Boolean emptyTest() {
                return true;
            }

            @Override
            public Boolean exceptionCatchingThrowing() {
                return true;
            }

            @Override
            public Boolean generalFixture() {
                return true;
            }

            @Override
            public Boolean mysteryGuest() {
                return true;
            }

            @Override
            public Boolean printStatement() {
                return true;
            }

            @Override
            public Boolean redundantAssertion() {
                return true;
            }

            @Override
            public Boolean sensitiveEquality() {
                return true;
            }

            @Override
            public Boolean verboseTest() {
                return true;
            }

            @Override
            public Boolean sleepyTest() {
                return true;
            }

            @Override
            public Boolean lazyTest() {
                return true;
            }

            @Override
            public Boolean unknownTest() {
                return true;
            }

            @Override
            public Boolean ignoredTest() {
                return true;
            }

            @Override
            public Boolean resourceOptimism() {
                return true;
            }

            @Override
            public Boolean magicNumberTest() {
                return true;
            }

            @Override
            public Integer maxStatements() {
                return 30;
            }
        };
        this.config = conf;
    }

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
        setConfig();
        JNoseCore jnose = new JNoseCore(this.config,3);
        try {
            List<TestClass> lista = jnose.getFilesTest(getRepositorio());
            for(TestClass testClass : lista){
                for (TestSmell testSmell : testClass.getListTestSmell()){
                    listener.getLogger().println(
                            testClass.getProjectName() + ";" +
                                    testClass.getPathFile() + ";" +
                                    testClass.getProductionFile() + ";" +
                                    testClass.getJunitVersion() + ";" +
                                    testSmell.getName() + ";" +
                                    testSmell.getMethod() + ";" +
                                    testSmell.getRange()
                    );
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Symbol("greet")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value) throws IOException, ServletException {
            if (value.length() == 0){
                return FormValidation.error("Insira um endereço git");
            }
            if (!value.matches("((http|git|ssh|http(s)|file|\\/?)|"
                    + "(git@[\\w\\.]+))(:(\\/\\/)?)"
                    + "([\\w\\.@\\:/\\-~]+)(\\.git)(\\/)?")) {
                return FormValidation.warning("Endereço git errado. Insira novamente");
            }
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Jnose Test";
        }

    }

}
