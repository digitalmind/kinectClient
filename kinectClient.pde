import pKinect.PKinect;
import pKinect.SkeletonData;
import oscP5.*;
import netP5.*;
import controlP5.*;

PKinect kinect;
PFont font;
ArrayList<SkeletonData> bodies;

NetAddress remoteIp;
ControlP5 ctrl;

boolean tglHead;
boolean tglNeck;
boolean tglRightShoulder;
boolean tglLeftShoulder;
boolean tglSpine;
boolean tglRightElbow;
boolean tglLeftElbow;
boolean tglRightHand;
boolean tglLeftHand;
boolean tglRightHip;
boolean tglLeftHip;
boolean tglRightKnee;
boolean tglLeftKnee;
boolean tglRightFoot;
boolean tglLeftFoot;

void setup()
{
	size(1280,480,P3D);
	
	kinect = new PKinect(this);
	bodies = new ArrayList<SkeletonData>();
	smooth();
	remoteIp = new NetAddress("127.0.0.1",12000);
  initGui();
	font = loadFont("Consolas-14.vlw");
	textFont(font, 14);
}

void draw()
{

	frame.setTitle("FPS: "+(int)frameRate);
	background(0);
    drawPanelLines();

	image(kinect.GetDepth(), -10,0, 640,480);
  for(SkeletonData s : bodies)
  {
    drawSkeleton(s); 
  }
  
}

void initGui()
{
  ctrl = new ControlP5(this);
  Toggle head = ctrl.addToggle("tglHead",960,30, 15,15);
  Toggle neck = ctrl.addToggle("tglNeck",960,90, 15,15);
  Toggle leftShoulder = ctrl.addToggle("tglLeftShoulder", 900,90,15,15);
  Toggle rightShoulder = ctrl.addToggle("tglRightShoulder", 1020,90,15,15);
  Toggle spine = ctrl.addToggle("tglSpine", 960, 160, 15,15);
  Toggle leftElbow = ctrl.addToggle("tglLeftElbow",885, 180, 15,15);
  Toggle rightElbow = ctrl.addToggle("tglRightElbow",1035,180, 15,15);
  Toggle leftHand = ctrl.addToggle("tglLeftHand", 875, 260, 15, 15);
  Toggle rightHand = ctrl.addToggle("tglRightHand",1045, 260, 15,15);

  Toggle leftHip = ctrl.addToggle("tglLeftHip", 920, 250, 15,15);
  Toggle rightHip = ctrl.addToggle("tglRightHip",1000,250, 15,15 );
  Toggle leftKnee = ctrl.addToggle("tglLeftKnee", 905, 360, 15,15);
  Toggle rightKnee = ctrl.addToggle("tglRightKnee",1015, 360, 15,15);
  Toggle leftFoot = ctrl.addToggle("tglLeftFoot", 905, 450, 15, 15);
  Toggle rightFoot = ctrl.addToggle("tglRightFoot",1015, 450, 15,15);
  
  head.setLabelVisible(false);
  neck.setLabelVisible(false);
  leftShoulder.setLabelVisible(false);
  rightShoulder.setLabelVisible(false);
  spine.setLabelVisible(false);
  leftElbow.setLabelVisible(false);
  rightElbow.setLabelVisible(false);
  leftHand.setLabelVisible(false);
  rightHand.setLabelVisible(false);
  leftHip.setLabelVisible(false);
  rightHip.setLabelVisible(false);
  leftKnee.setLabelVisible(false);
  rightKnee.setLabelVisible(false);
  leftFoot.setLabelVisible(false);
  rightFoot.setLabelVisible(false);
}

void drawPanelLines()
{
  stroke(255,0,0);
  strokeWeight(3);
  line(968, 38, 968,98);
}

void drawPosition(SkeletonData _s) 
{
  noStroke();
  fill(0, 100, 255);
  String s1 = str(_s.dwTrackingID);
  text(s1, _s.position.x*width/2, _s.position.y*height/2);
}

void drawSkeleton(SkeletonData _s) 
{
  // Body
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_HEAD, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_CENTER);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_CENTER, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_LEFT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_CENTER, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_RIGHT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_CENTER, 
  PKinect.NUI_SKELETON_POSITION_SPINE);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_LEFT, 
  PKinect.NUI_SKELETON_POSITION_SPINE);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_SPINE);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SPINE, 
  PKinect.NUI_SKELETON_POSITION_HIP_CENTER);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_HIP_CENTER, 
  PKinect.NUI_SKELETON_POSITION_HIP_LEFT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_HIP_CENTER, 
  PKinect.NUI_SKELETON_POSITION_HIP_RIGHT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_HIP_LEFT, 
  PKinect.NUI_SKELETON_POSITION_HIP_RIGHT);

  // Left Arm
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_LEFT, 
  PKinect.NUI_SKELETON_POSITION_ELBOW_LEFT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_ELBOW_LEFT, 
  PKinect.NUI_SKELETON_POSITION_WRIST_LEFT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_WRIST_LEFT, 
  PKinect.NUI_SKELETON_POSITION_HAND_LEFT);

  // Right Arm
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_SHOULDER_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_ELBOW_RIGHT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_ELBOW_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_WRIST_RIGHT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_WRIST_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_HAND_RIGHT);

  // Left Leg
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_HIP_LEFT, 
  PKinect.NUI_SKELETON_POSITION_KNEE_LEFT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_KNEE_LEFT, 
  PKinect.NUI_SKELETON_POSITION_ANKLE_LEFT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_ANKLE_LEFT, 
  PKinect.NUI_SKELETON_POSITION_FOOT_LEFT);

  // Right Leg
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_HIP_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_KNEE_RIGHT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_KNEE_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_ANKLE_RIGHT);
  DrawBone(_s, 
  PKinect.NUI_SKELETON_POSITION_ANKLE_RIGHT, 
  PKinect.NUI_SKELETON_POSITION_FOOT_RIGHT);
}

void DrawBone(SkeletonData _s, int _j1, int _j2) 
{
  noFill();
  strokeWeight(4);
  stroke(255, 0, 0);
  if (_s.skeletonPositionTrackingState[_j1] != PKinect.NUI_SKELETON_POSITION_NOT_TRACKED &&
    _s.skeletonPositionTrackingState[_j2] != PKinect.NUI_SKELETON_POSITION_NOT_TRACKED) {
    line(_s.skeletonPositions[_j1].x*width/2, 
    _s.skeletonPositions[_j1].y*height, 
    _s.skeletonPositions[_j2].x*width/2, 
    _s.skeletonPositions[_j2].y*height);
  }
}

void appearEvent(SkeletonData _s) 
{
  if (_s.trackingState == PKinect.NUI_SKELETON_NOT_TRACKED) 
  {
    return;
  }
  synchronized(bodies) {
    bodies.add(_s);
  }
}

void disappearEvent(SkeletonData _s) 
{
  println("and it's gone");
  synchronized(bodies) {
    for (int i=bodies.size()-1; i>=0; i--) 
    {
      if (_s.dwTrackingID == bodies.get(i).dwTrackingID) 
      {
        bodies.remove(i);
      }
    }
  }
}

void moveEvent(SkeletonData _b, SkeletonData _a) 
{
  if (_a.trackingState == PKinect.NUI_SKELETON_NOT_TRACKED) 
  {
    return;
  }
  synchronized(bodies) {
    for (int i=bodies.size()-1; i>=0; i--) 
    {
      if (_b.dwTrackingID == bodies.get(i).dwTrackingID) 
      {
        bodies.get(i).copy(_a);
        break;
      }
    }
  }
}