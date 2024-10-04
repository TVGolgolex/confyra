plugins {
    id("java")
    id("maven-publish")
}

dependencies {
    implementation(libs.quala.bom)
    implementation(libs.lombok)
    implementation(project(":confyra-config"))
    annotationProcessor(libs.lombok)
}