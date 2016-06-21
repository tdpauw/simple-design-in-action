node {
    stage 'Checkout'
    git url: 'http://192.168.99.100:3000/thipau/simple-design-in-action.git'
    stage 'Define Version'
    sh 'echo $(git rev-parse --short HEAD) > revision'
    def revision = readFile('revision').trim()
    def mvnHome = tool 'M3'
    sh "${mvnHome}/bin/mvn validate release-candidate:updateVersion -Drevision=${revision}"
    stage 'Commit Build'
    sh "${mvnHome}/bin/mvn verify"
}
