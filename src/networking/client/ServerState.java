package networking.client;

import java.util.ArrayList;

import math.vec2;

public class ServerState {
	public vec2 clientPosition = new vec2(0.0f, 0.0f);
	public vec2 serverPosition = new vec2(0.0f, 0.0f);
	public int numClientBullets = 0;
	public int numServerBullets = 0;
	public ArrayList<vec2> bulletPositions = new ArrayList<vec2>();
}
