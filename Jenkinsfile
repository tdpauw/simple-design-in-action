node {
    stage 'Checkout'
    git url: 'http://192.168.99.100:3000/thipau/simple-design-in-action.git'

    stage 'Update Version'
    def revision = gitShortCommitHash();

    mvn "validate release-candidate:updateVersion -Drevision=${revision}"
    def pom = readMavenPom file: 'pom.xml'
    currentBuild.displayName = pom.version

    stage 'Commit Build'
    mvn "clean install -pl simple-design-in-action-core -am"
    step([$class: 'JUnitResultArchiver', allowEmptyResults: true, testResults: './simple-design-in-action-core/target/surefire-reports/TEST-*.xml'])

    stage 'Code Quality'
    mvn "sonar:sonar -Dsonar.host.url=http://192.168.99.100:9000 -Dsonar.jdbc.url=jdbc:h2:tcp://192.168.99.100/sonar"
}

def mvn(goals) {
    def mvnHome = tool 'M3'
    sh "${mvnHome}/bin/mvn ${goals}"
}

def gitShortCommitHash() {
    sh 'echo $(git rev-parse --short HEAD) > revision'
    def revision = readFile('revision').trim()
    return revision;
}