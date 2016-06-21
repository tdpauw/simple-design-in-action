node {
    stage 'Checkout'
    git url: 'http://192.168.99.100:3000/thipau/simple-design-in-action.git'
    stage 'Commit Build'
    def mvnHome = tool 'M3'
    sh "${mvnHome}/bin/mvn verify"
}
