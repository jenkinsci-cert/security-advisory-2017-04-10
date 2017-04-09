# Companion scripts for 2017-04-10 Jenkins security advisory

These scripts are provided by the Jenkins security team to help you determine the potential impact of upgrading installed plugins to versions containing fixes for these vulnerabilities:

* SECURITY-123 (extensible-choice-parameter)
* SECURITY-256 (envinject)
* SECURITY-257 (email-ext)
* SECURITY-292 (groovy)
* SECURITY-348 (envinject)
* SECURITY-368 (lockable-resources)

See [the 2017-04-10 Jenkins security advisory](https://jenkins.io/security/advisory/2017-04-10/) for more information about these vulnerabilities.

These scripts are intended to be run in either of the following modes on a recent release of Jenkins (tested on 2.32.2):

1. In the Jenkins script console at _Manage Jenkins » Script Console_
2. Using the `groovy` CLI command

In either case, their findings need to be reviewed afterwards.

These scripts are provided as is, without warranty of any kind. We make no guarantees regarding its ability to find affected job configurations.

MIT License.
