def cmd = new CommandLineParameter()

cmd.parse(args)

if (cmd.needsToShowHelp()) {
    cmd.usage()
    return
}

def api = new RepositoryApi()
cmd.setup(api)

api.doRequest { id ->
    println id
}




def class CommandLineParameter {
    
    def cli
    def opt
    
    def CommandLineParameter() {
        cli = new CliBuilder(usage: ' ')
        
        cli.with {
            g longOpt: 'groupId', args: 1, 'specify groupId'
            a longOpt: 'artifactId', args: 1, 'specify artifactId'
            v longOpt: 'version', args: 1, 'specify version'
            _ longOpt: 'allVersions', 'show all versions. this option is enabled only when using "g", "a", "v" options.'
            q longOpt: 'query', args: 1, 'search by this query text.'
            _ longOpt: 'max', args: 1, 'limit number of result. default is 20.'
            h longOpt: 'help', 'show usage'
        }
    }
    
    def parse(args) {
        opt = cli.parse(args)
        if (!opt) System.exit(-1)
    }
    
    def needsToShowHelp() {
        opt.h || notSetAnyQueryOptions()
    }
    
    def notSetAnyQueryOptions() {
        !opt.q && !opt.g && !opt.a && !opt.v
    }
    
    def usage() {
        cli.usage()
        println """
        |  if "q" and any of "g", "a", "v" options are used, "q" option is only enabled.
        |""".stripMargin()
    }
    
    def setup(api) {
        if (opt.q) {
            api.query(opt.q)
        } else {
            if (opt.g) {
                api.groupId(opt.g)
            }
            if (opt.a) {
                api.artifactId(opt.a)
            }
            if (opt.v) {
                api.version(opt.v)
            }
            if (opt.allVersions) {
                api.allVersions()
            }
        }

        if (opt.max) {
            api.rows = opt.max
        }
    }
}

import groovy.json.JsonSlurper

def class RepositoryApi {
    
    def rows = 20
    def format = 'json'
    def query = ''
    def allVersions = false
    
    void doRequest(idIterator) {
        def jsonText = requestJsonText()
        def jsonObject = parseJson(jsonText)
        def ids = collectIds(jsonObject)
        
        ids.sort().each(idIterator)
    }
    
    private def requestJsonText() {
        def con = buildUrl().openConnection()
        con.setRequestMethod('GET')
        con.connect()
        con.content.text
    }
    
    private def buildUrl() {
        new URL(
            'http://search.maven.org/solrsearch/select?' +
            "rows=${rows}" +
            "&wt=${format}" +
            "&q=${query}" +
            (allVersions ? '&core=gav' : '')
        )
    }
    
    private def parseJson(json) {
        def jsonSlurper = new JsonSlurper()
        jsonSlurper.parseText(json)
    }
    
    private def collectIds(jsonObject) {
        jsonObject.response.docs.collect { toId(it) }
    }
    
    private String toId(doc) {
        allVersions ? doc.id
                    : doc.id + ':' + doc.latestVersion
    }
    
    def query(query) {
        this.query = encode(query)
    }
    
    def groupId(groupId) {
        appendQuery 'g', groupId
    }
    
    def artifactId(artifactId) {
        appendQuery 'a', artifactId
    }
    
    def version(version) {
        appendQuery 'v', version
    }
    
    private void appendQuery(parameterName, keyword) {
        def q = this.query.isEmpty() ? /${parameterName}:"${keyword}"/
                                     : / AND ${parameterName}:"${keyword}"/
        this.query += encode(q)
    }
    
    def allVersions() {
        allVersions = true
    }
    
    private String encode(text) {
        URLEncoder.encode(text, 'UTF-8')
    }
}

def test() {
    // #RepositoryApi
    // add groupId when query is empty
    def repo = new RepositoryApi()
    repo.groupId 'ggg'
    
    assert repo.query == 'g%3A%22ggg%22'
    
    // add groupId when query is not empty
    repo = new RepositoryApi()
    repo.query 'qqq'
    repo.groupId 'fuga'
    
    assert repo.query == 'qqq+AND+g%3A%22fuga%22'
    
    // add artifactId when query is empty
    repo = new RepositoryApi()
    repo.artifactId 'xxx'
    
    assert repo.query == 'a%3A%22xxx%22'
    
    // add artifactId when query is not empty
    repo = new RepositoryApi()
    repo.query 'qqq'
    repo.artifactId 'xxx'
    
    assert repo.query == 'qqq+AND+a%3A%22xxx%22'
    
    // add version when query is empty
    repo = new RepositoryApi()
    repo.version 'vvv'
    
    assert repo.query == 'v%3A%22vvv%22'
    
    // add version when query is not empty
    repo = new RepositoryApi()
    repo.query 'qqq'
    repo.version 'vvv'
    
    assert repo.query == 'qqq+AND+v%3A%22vvv%22'
    
    
    // #CommandLineParameter
    // not set any options
    cmd = new CommandLineParameter()
    cmd.parse([])
    
    assert cmd.needsToShowHelp() == true
    
    // set h option
    def cmd = new CommandLineParameter()
    cmd.parse(['-h'])
    
    assert cmd.needsToShowHelp() == true
    
    // set q option
    cmd = new CommandLineParameter()
    cmd.parse(['-q', 'qqq'])
    
    assert cmd.needsToShowHelp() == false
    
    // set g option
    cmd = new CommandLineParameter()
    cmd.parse(['-g', 'ggg'])
    
    assert cmd.needsToShowHelp() == false
    
    // set a option
    cmd = new CommandLineParameter()
    cmd.parse(['-a', 'aaa'])
    
    assert cmd.needsToShowHelp() == false
    
    // set v option
    cmd = new CommandLineParameter()
    cmd.parse(['-v', 'vvv'])
    
    assert cmd.needsToShowHelp() == false
}
