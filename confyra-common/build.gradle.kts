plugins {
    id("java")
    id("maven-publish")
}

dependencies {
    compileOnly(libs.quala.bom)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

publishing {
    repositories {
        maven {
            name = "lumesolutions"
            url = uri(
                if (version.toString().endsWith("SNAPSHOT"))
                    "https://repository02.lumesolutions.de/repository/lumesolutions-intern-snapshot/" else
                    "https://repository02.lumesolutions.de/repository/lumesolutions-intern-release/"
            )
            credentials {
                username = project.findProperty("lumesolutions_user") as String?
                password = project.findProperty("lumesolutions_password") as String?
            }
        }
    }

    publications {
        create<MavenPublication>("lumesolutions") {
            groupId = groupId
            artifactId = artifactId
            version = version

            from(components["java"])
        }
    }
}