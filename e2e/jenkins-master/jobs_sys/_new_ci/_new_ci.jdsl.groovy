job("_new_ci") {
	description()
	keepDependencies(false)
	parameters {
		stringParam("jobname_ci", "jenlib_pipe_master_ci_X", "jenlib_pipe_master_ci")
		stringParam("github_p", "gzvulon/jenlib", "github_p")
		stringParam("jengroovy_p", "e2e/data4test/parallel_tasks_jen/build.groovy", "jengroovy_p")
	}
	disabled(false)
	concurrentBuild(false)
	steps {
		dsl {
			text("""// def jobname_ci = '{{.JOB_NAME}}' // jenlib_pipe_master_ci
// def github_p = '{{.GITHUB_PATH}}' // "gzvulon/jenlib"
// def jengroovy_p = '{{.JENKINS_FILE_PATH}}' // "e2e/data4test/parallel_tasks_jen/build.groovy"

pipelineJob("\${jobname_ci}") {
	description()
	keepDependencies(false)
	definition {
		cpsScm {
			scm {
				git {
					remote {
						github("\${github_p}", "https")
					}
					branch("*/master")
				}
			}
			scriptPath("\${jengroovy_p}")
		}
	}
	disabled(false)
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}""")
			ignoreExisting(false)
			removeAction("IGNORE")
			removeViewAction("IGNORE")
			lookupStrategy("JENKINS_ROOT")
		}
	}
	configure {
		it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
			'autoRebuild'('false')
			'rebuildDisabled'('false')
		}
	}
}
