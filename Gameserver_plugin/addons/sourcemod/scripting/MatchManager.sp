#pragma semicolon 1

#define DEBUG

#define PLUGIN_AUTHOR "ripaimcsgo.xyz"
#define PLUGIN_VERSION "1.0"

#define CANCELLED 0
#define PLAYING 1
#define FINISHED 2

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
char g_sIP[20];
char g_sPort[6];

bool g_bEnable = true;
bool g_bIsLive = false;

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
	
}

public void OnConfigsExecuted()
{
	wm_config_api_url.GetString(g_APIURL, sizeof(g_APIURL));
	wm_config_api_key.GetString(g_APIKey, sizeof(g_APIKey));
	g_bEnable = wm_enable_api.BoolValue;
	GetConVarString(ip, g_sIP, sizeof(g_sIP));
	GetConVarString(port, g_sPort, sizeof(g_sPort));
}

public void OnClientPostAdminCheck(int client)
{
	if (g_bEnable)
	{
		if (!IsClientAuthorized(client))
		{
			KickClient(client, "Verification problem, Please reconnect");
		}
		if(g_iOrderID == 0 && g_iMatchID == 0)
		{
			GetMatchInfo();
			KickClient(client, "There is no match has been registered on this server");
		}
	}
}


/* Functions (Some code from G5WS plugin by Phlex Plexico. Thanks!) */
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

public void OnClientDisconnect_Post(int client)
{
	if (!RealPlayerExist() && g_bIsLive)
	{
		UpdateMatchState(CANCELLED);
		UpdateMatchOrderStatus(CANCELLED, "Players disconnected when playing.");
		
		g_iOrderID = 0;
		g_iMatchID = 0;
		g_bIsLive = false;
	}
}

/* Request callback */
public void GetMatchCallback(HTTPResponse response, any value) {
	char sData[1024];
	if (response.Status == HTTPStatus_NotFound) {
		PrintToServer("[ERR] API request failed, HTTP status code: %d", response.Status);
		response.Data.ToString(sData, sizeof(sData), JSON_INDENT(4));
		
		PrintToServer("[ERR] Response:\n%s", sData);
		//KickClient(value, "There is no match has been registered on this server");
	}
	else
	{
		//g_bcanJoin = true;
		char jsonres[512];
		response.Data.ToString(jsonres, sizeof(jsonres));
		JSONObject obj = JSONObject.FromString(jsonres);
		g_iOrderID = obj.GetInt("OrderID");
		delete obj;
	}
}

public void InsertCallback(HTTPResponse response, any value) {
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

public void UpdateMatchStateCallback(HTTPResponse response, any value) {
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
	}
}

public void UpdateMatchOrderStatusCallback(HTTPResponse response, any value) {
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
	}
}

public void UpdateMatchScoreCallback(HTTPResponse response, any value) {
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
	}
}

/* Make request to API Server */
static HTTPRequest GetMatchInfo()
{
	HTTPRequest req = CreateRequest("/match/check-match-info");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("ip", "%s", g_sIP);
	req.AppendQueryParam("port", "%s", g_sPort);
	if (req != null)
	{
		req.Get(GetMatchCallback);
	}
}

static HTTPRequest InsertNewMatch()
{
	HTTPRequest req = CreateRequest("/match/insert");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("orderID", "%d", g_iOrderID);
	if (req != null)
	{
		req.Post(new JSONObject(), InsertCallback);
	}
}

static HTTPRequest UpdateMatchState(int state)
{
	HTTPRequest req = CreateRequest("/match/update");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("matchID", "%d", g_iMatchID);
	req.AppendQueryParam("state", "%d", state);
	if (req != null)
	{
		req.Post(new JSONObject(), UpdateMatchStateCallback);
	}
}

static HTTPRequest UpdateMatchOrderStatus(int status, char[] comment)
{
	HTTPRequest req = CreateRequest("/match/order/update");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("orderID", "%d", g_iOrderID);
	req.AppendQueryParam("status", "%d", status);
	req.AppendQueryParam("comment", "%s", comment);
	if (req != null)
	{
		req.Post(new JSONObject(), UpdateMatchOrderStatusCallback);
	}
}

static HTTPRequest UpdateMatchScore(int ct_score, int t_score, const char[] ct_name, const char[] t_name)
{
	HTTPRequest req = CreateRequest("/match/score/update");
	req.AppendQueryParam("Key", "%s", g_APIKey);
	req.AppendQueryParam("matchID", "%d", g_iMatchID);
	req.AppendQueryParam("ct_score", "%d", ct_score);
	req.AppendQueryParam("t_score", "%d", t_score);
	req.AppendQueryParam("ct_name", "%s", ct_name);
	req.AppendQueryParam("t_name", "%s", t_name);
	if (req != null)
	{
		req.Post(new JSONObject(), UpdateMatchScoreCallback);
	}
}

/* Event handler */
public void OnLiveOn3() {
	if (g_bEnable)
	{
		InsertNewMatch();
		g_bIsLive = true;
	}
}

public void OnRoundEnd(const char[] ct_name, int ct_score, int t_score, const char[] t_name)
{
	if(g_bIsLive)
	{
		UpdateMatchScore(ct_score, t_score, ct_name, t_name);
	}
}

public void OnEndMatch(const char[] ct_name, int ct_score, int t_score, const char[] t_name)
{
	if (g_bEnable)
	{
		UpdateMatchState(FINISHED);
		UpdateMatchScore(ct_score, t_score, ct_name, t_name);
		g_iOrderID = 0;
		g_iMatchID = 0;
		g_bIsLive = false;
	}
}

/* Check if any player exist on the server. */
/* Code by Alex Dragokas - https://github.com/dragokas/ */
bool RealPlayerExist(int iExclude = 0)
{
	for (int client = 1; client <= MaxClients; client++)
	{
		if (client != iExclude && IsClientConnected(client))
		{
			if (!IsFakeClient(client))
			{
				return true;
			}
		}
	}
	return false;
}

