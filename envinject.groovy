/*

It is very likely this this produces an INCOMPLETE list of affected
uses of this plugin. It is only intended to give you a general idea
of how much upgrading this plugin will affect your instance.

*/

import hudson.util.VersionNumber
import hudson.model.*
import hudson.*
import jenkins.model.*
import jenkins.*

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

def pluginName = "envinject"
def fixedVersion = "2.0"

void checkEnvinjectProperty(Item item) {
	if (!(item instanceof Job)) {
		return
	}
	def prop = item.getProperty(org.jenkinsci.plugins.envinject.EnvInjectJobProperty)
	if (prop == null) {
		return
	}

	def envinject = prop.info

	if (envinject.groovyScriptContent != null && envinject.groovyScriptContent.trim() != '') {
		noteItem(item, "Job Property: Groovy Script")
	}

	if (envinject.isLoadFilesFromMaster()) {
		noteItem(item, "Job Property: Load from master")
	}
}

void checkEnvinjectWrapper(Item item) {
	if (!(item instanceof BuildableItemWithBuildWrappers)) {
		return
	}
	def wrapper = item.buildWrappersList.get(org.jenkinsci.plugins.envinject.EnvInjectBuildWrapper)
	if (wrapper == null) {
		return
	}

	def envinject = wrapper.info

	if (envinject.groovyScriptContent != null && envinject.groovyScriptContent.trim() != '') {
		noteItem(item, "Build Wrapper: Groovy Script")
	}

	if (envinject.isLoadFilesFromMaster()) {
		noteItem(item, "Build Wrapper: Load from master")
	}
}

void check(Item item) {
	checkEnvinjectProperty(item)
	checkEnvinjectWrapper(item)
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

boolean noteItem(Item item, def note) {
	println(item.getFullName() + ': ' + note?.toString())
}

if (Jenkins.instance.pluginManager.getPlugin(pluginName)?.isOlderThan(new VersionNumber(fixedVersion))?:false) {
	println "This instance has a vulnerable version of '" + pluginName + "' installed (older than " + fixedVersion + ")."
	Jenkins.instance.getAllItems().each { item ->
		check(item)
	}
}

return
