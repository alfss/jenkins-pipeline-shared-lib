class wat implements Serializable {
    private String name
    def setName(value) {
        name = value
    }
    def getName() {
        name
    }
    def sayWat(message) {
        echo "${name}: WAAAAAAAAAAAAT!, ${message}"
    }
}