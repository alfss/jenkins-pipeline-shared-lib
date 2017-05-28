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

    class DockerBuildParams {
        /** image name*/
        String name
        /** image tag*/
        String tag
        /** path to Dockerfile*/
        String dockerFile
        /** build args */
        Map buildArgs
        /**  root dir for build image */
        String buildRoot
    }

    /**
     * Exec build image
     */
    def build(DockerBuildParams params) {
        steps.sh commandDocker(params.name, params.tag, params.buildRoot, params.dockerFile, params.buildArgs)
    }

    /** Map all parameters to DockerBuildParams class */
    def build(Map m) { method m as DockerBuildParams }

    /**
     * @param name image name
     * @param tag image tag
     * @param dockerFile path to docker file
     * @param buildArgs (Map) build args for `docker build`
     * @param buildRoot root dir for build image
     * @return docker build command
     */
    def commandDocker(String name, String tag, String buildRoot, String dockerFile, Map buildArgs) {
        def bArgs = ""
        buildArgs.each { String item -> bArgs += "--build-arg ${item.key}=${item.value}" }
        return  "docker build ${bArgs} -f ${dockerFile} -t ${name}:${tag} ${buildRoot}"
    }
}