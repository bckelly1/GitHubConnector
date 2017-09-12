# GitHubConnector

This is a quick tool to get all pull requests associated with a user account on GitHub. It will only poll for public repositories and check if those repositories have any pull requests associated with them.

It uses a very simple REST API client to call GET on URLs on the GitHub v3 API.

## Future Plans
I hope to make this a more generic library that is easily callable for simple REST API calls. Making the REST calls on non-blocking threads would also be a good idea.