package net.alfss.helper

/**
 * Build docker image
 * @author Sergei Kravchuk <alfss.obsd@gmail.com>
 * @see <a href="http://alfss.net">http://alfss.net</a>
 *
 */
class BuildDockerHelper implements Serializable {

    def steps
    BuildDockerHelper(steps) { this.steps = steps }

    class DockerBuildParams  implements Serializable {
        /** image name*/
        String name
        /** image tag*/
        String tag
        /** path to Dockerfile*/
        String dockerFile
        /** enable make Dockerfile in ws*/
        Boolean enableMakeDockerFile = false
        /** build args */
        Map buildArgs
        /**  root dir for build image */
        String buildRoot
    }

    /**
     * Exec build image
     */
    def build(DockerBuildParams params) {
        if (params.enableMakeDockerFile) {
            makeDockerFile(params.dockerFile)
        }
        steps.echo "Run build"
        steps.sh commandDocker(params.name, params.tag, params.buildRoot, params.dockerFile, params.buildArgs)
    }

    /** Map all parameters to DockerBuildParams class */
    @NonCPS
    def build(Map m) { build m as DockerBuildParams }

    /**
     * @param name image name
     * @param tag image tag
     * @param dockerFile path to docker file
     * @param buildArgs (Map) build args for `docker build`
     * @param buildRoot root dir for build image
     * @return docker build command
     */
    @NonCPS
    def commandDocker(String name, String tag, String buildRoot, String dockerFile, Map buildArgs) {
        steps.echo "Generate docker command"
        def bArgs = ""
        buildArgs.each { item -> bArgs += " --build-arg ${item.key}=${item.value}" }
        def command = "docker build ${bArgs} -f ${dockerFile} -t ${name}:${tag} ${buildRoot}"
        return command
    }

    def makeDockerFile(String dockerFile) {
        steps.echo "Generate Dockerfile"
        def dockerFileData = steps.libraryResource 'net/alfss/helper/docker/Dockerfile'
        steps.writeFile file: 'Dockerfile', text: dockerFileData
    }
}