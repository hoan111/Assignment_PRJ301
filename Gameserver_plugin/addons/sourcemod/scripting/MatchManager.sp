#pragma semicolon 1

#define DEBUG

#define PLUGIN_AUTHOR "ripaimcsgo.xyz"
#define PLUGIN_VERSION "1.0"

#include <sourcemod>
#include <sdktools>
#include <cstrike>
#include <warmod>
#include <ripext>
//#include <sdkhooks>

#pragma newdecls required

EngineVersion g_Game;

/* Convars*/
ConVar wm_enable_api;
ConVar wm_config_api_url;
ConVar wm_config_api_key;
ConVar ip;
ConVar port;

/* Variables */
char g_APIKey[128];
char g_APIURL[1024];
int g_iEnable;
char g_sIP[20];
char g_sPort[6];

bool g_bcanJoin = false;

static int g_iOrderID = 0;
static int g_iMatchID = 0;
public Plugin myinfo = 
{
	name = "Match management for Warmod[BFG]", 
	author = PLUGIN_AUTHOR, 
	description = "Match management for Warmod[BFG] using RIPEXT", 
	version = PLUGIN_VERSION, 
	url = "ripaimcsgo.xyz"
};

public void OnPluginStart()
{
	g_Game = GetEngineVersion();
	if (g_Game != Engine_CSGO)
	{
		SetFailState("This plugin is for CSGO only.");
	}
	AutoExecConfig();
	/* Register convars */
	wm_enable_api = CreateConVar("wm_enable_api", "1", "Enable match management via API", FCVAR_NOTIFY, true, 0.0, true, 1.0);
	wm_config_api_url = CreateConVar("wm_config_api_url", "", "API url you want to receive and update.", FCVAR_NOTIFY, false, 0.0, false, 1.0);
	wm_config_api_key = CreateConVar("wm_config_api_key", "", "API key", FCVAR_NOTIFY, false, 0.0, false, 1.0);
	ip = FindConVar("ip");
	port = FindConVar("hostport");
	
	RegConsoleCmd("sm_check", printInfo, "test");
}

public void OnConfigsExecuted()
{
	wm_config_api_url.GetString(g_APIURL, sizeof(g_APIURL));
	wm_config_api_key.GetString(g_APIKey, sizeof(g_APIKey));
	GetConVarString(ip, g_sIP, sizeof(g_sIP));
	GetConVarString(port, g_sPort, sizeof(g_sPort));
}

/* Functions */
static HTTPRequest CreateRequest(const char[] apiMethod, any:...) {
	char url[1024];
	Format(url, sizeof(url), "%s%s", g_APIURL, apiMethod);
	char formattedUrl[1024];
	VFormat(formattedUrl, sizeof(formattedUrl), url, 2);
	PrintToServer("Trying to create request to url %s", formattedUrl);
	
	HTTPRequest req = new HTTPRequest(formattedUrl);
	if (StrEqual(g_APIKey, "")) {
		// Not using a web interface.
		return null;
	} else if (req == INVALID_HANDLE) {
		PrintToServer("Failed to create request to %s", formattedUrl);
		return null;
	} else {
		return req;
	}
}

public static void GetMatchCallback(HTTPResponse response, any value) {
    char sData[1024];
    if (response.Status == HTTPStatus_NotFound) {
        PrintToServer("[ERR] API request failed, HTTP status code: %d", response.Status);
        response.Data.ToString(sData, sizeof(sData), JSON_INDENT(4));
        
        PrintToServer("[ERR] Response:\n%s", sData);
        g_bcanJoin = false;
    }
    else
    {
    	g_bcanJoin = true;
    	char jsonres[512];
    	response.Data.ToString(jsonres, sizeof(jsonres));
    	JSONObject obj = JSONObject.FromString(jsonres);
    	g_iOrderID = obj.GetInt("OrderID");
    	delete obj;
   	}
}

public static void InsertCallback(HTTPResponse response, any value) {
    char sData[1024];
    if (response.Status == HTTPStatus_InternalServerError) {
        PrintToServer("[ERR] API request failed, HTTP status code: %d", response.Status);
        response.Data.ToString(sData, sizeof(sData), JSON_INDENT(4));
        
        PrintToServer("[ERR] Response:\n%s", sData);
    }
    else
    {
    	char jsonres[512];
    	response.Data.ToString(jsonres, sizeof(jsonres));
    	JSONObject obj = JSONObject.FromString(jsonres);
    	g_iMatchID = obj.GetInt("MatchID");
    	delete obj;
   	}
}

static HTTPRequest GetMatchInfo()
{
	HTTPRequest req = CreateRequest("/match/check-match-info");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("ip", "%s", g_sIP);
	req.AppendQueryParam("port", "%s", g_sPort);
	if(req != null)
	{
		req.Get(GetMatchCallback);
	}
}

static HTTPRequest InsertNewMatch()
{
	HTTPRequest req = CreateRequest("/match/insert");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("orderID", "%d", g_iOrderID);
	if(req != null)
	{
		req.Post(new JSONObject(), InsertCallback);
	}
}

public void OnClientConnected(int client)
{
	GetMatchInfo();
	if(!g_bcanJoin)
	{
		KickClient(client, "There is no match has been registered on this server");
	}
}

public void OnLiveOn3() {
	InsertNewMatch();
}


public Action printInfo(int client, int args) {
	PrintToServer("g_APIURL: %s", g_APIURL);
	PrintToServer("g_APIKey: %s", g_APIKey);
	PrintToServer("g_sIP: %s", g_sIP);
	PrintToServer("g_sPort: %s", g_sPort);
	PrintToServer("g_iOrderID: %d", g_iOrderID);
	PrintToServer("g_iMatchID: %d", g_iMatchID);
}

