Contributing to Globe API
===

Setting up your machine with the Globe API repository and your fork
------

1. Fork the main Globe API repository (https://github.com/globelabs/api)
2. Fire up your local terminal and clone the MAIN GLOBE API REPOSITORY (git clone git://github.com/globelabs/api.git)
3. Add your FORKED GLOBE API REPOSITORY as a remote (git remote add fork git@github.com:github_username/api.git)

Making pull requests
------

1. Before anything, make sure to update the MAIN GLOBE API REPOSITORY. (git checkout master; git pull origin master)
2. Once updated with the latest code, create a new branch with a branch name describing what your changes are (git checkout -b bugfix/fix-ruby-sms-auth) Possible types:
   - bugfix
   - language
   - feature
   - improvement
3. Make your code changes. Always make sure to sign-off (-s) on all commits made (git commit -s -m "Commit message")
4. Once you've committed all the code to this branch, push the branch to your FORKED GLOBE API REPOSITORY (git push fork bugfix/fix-ruby-auth)
5. Go back to your FORKED GLOBE API REPOSITORY on GitHub and submit a pull request.
6. An Globe API developer will review your code and merge it in when it has been classified as suitable.
