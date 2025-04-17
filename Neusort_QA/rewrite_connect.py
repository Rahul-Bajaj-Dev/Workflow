from mitmproxy import http
import re

# Define mitmproxy functions to log requests and responses
def request(flow: http.HTTPFlow):
    """
    This function logs and modifies HTTP requests.
    """
    original_url = flow.request.url
    print(f"Captured Request URL: {original_url}")
    print(f"Rewritten to HTTP URL: {flow.request.url}")


    if original_url.startswith("https://20.40.47.123:4000"):
        flow.request.url = original_url.replace("https://20.40.47.123:4000", "http://20.40.47.123:4000")
        print(f"Rewritten to HTTP URL: {flow.request.url}")

    elif original_url.startswith("https://20.40.47.123:3000"):
        flow.request.url = original_url.replace("https://20.40.47.123:3000", "http://20.40.47.123:3000")
        print(f"Rewritten to HTTP URL: {flow.request.url}")

    # Check if the URL is exactly "https://api.neusort.com"
    elif original_url == "https://api.neusort.com":
        rewritten_url = original_url.replace("https://api.neusort.com", "http://20.40.47.123:8080")
        flow.request.url = rewritten_url
        print(f"Rewritten Base URL: {rewritten_url}")

    # Check if the URL contains a path (e.g., /getcoin)
    elif re.match(r"https://api\.neusort\.com/.+", original_url):  # Matches any path under api.neusort.com
        rewritten_url = original_url.replace("https://api.neusort.com", "http://20.40.47.123:8080")
        flow.request.url = rewritten_url
        print(f"Rewritten Path URL: {rewritten_url}")

    elif re.match(r"https://evalai-server\.gentlebay-8bbb7147\.centralindia\.azurecontainerapps\.io/.*", original_url):
        rewritten_url = original_url.replace("https://evalai-server.gentlebay-8bbb7147.centralindia.azurecontainerapps.io", "http://20.40.47.123:8080")
        flow.request.url = rewritten_url
        print(f"Rewritten URL: {rewritten_url}")

    elif re.match(r"https://streamingserver\.hyrr\.app/.*", original_url):
        rewritten_url = original_url.replace("https://streamingserver.hyrr.app", "http://20.40.47.123:3001")
        flow.request.url = rewritten_url
        print(f"Rewritten URL: {rewritten_url}")

    elif re.match(r"wss://streamingserver\.hyrr\.app/socket\.io/.*", original_url):
        rewritten_url = original_url.replace("wss://streamingserver.hyrr.app", "ws://20.40.47.123/socket.io")
        flow.request.url = rewritten_url
        print(f"Rewritten WebSocket URL: {rewritten_url}")


def response(flow: http.HTTPFlow):
    """
    This function logs the HTTP responses.
    """
    print(f"Response Status: {flow.response.status_code} {flow.request.url}")
    print("----------------------------------")

