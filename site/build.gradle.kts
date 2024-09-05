import com.varabyte.kobweb.gradle.application.extensions.AppBlock.LegacyRouteRedirectStrategy
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.link
import kotlinx.html.script

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.serialization.plugin)
    // alias(libs.plugins.kobwebx.markdown)
}

group = "com.tafakkur.blogweb"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")

            head.add {
//                link {
//                    rel = "stylesheet"
//                    href = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
//                }
//                script {
//                    src = "https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
//                }
                script {
                    src = "/highlight.min.js"
                }
                link {
                    rel = "stylesheet"
                    href = "/github-dark.css"
                }
            }

        }

        // Only legacy sites need this set. Sites built after 0.16.0 should default to DISALLOW.
        // See https://github.com/varabyte/kobweb#legacy-routes for more information.
//        legacyRouteRedirectStrategy.set(LegacyRouteRedirectStrategy.DISALLOW)
    }
}

kotlin {
    configAsKobwebApplication("blogweb", includeServer = true)

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
        }

        jsMain.dependencies {
            implementation(compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.silk.icons.mdi)
            implementation(libs.kotlinx.serialization)
            // implementation(libs.kobwebx.markdown)

        }
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
            implementation(libs.kotlinx.serialization)
        }
    }
}
