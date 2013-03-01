import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import pKinect.PKinect; 
import pKinect.SkeletonData; 
import oscP5.*; 
import netP5.*; 
import controlP5.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class kinectClient extends PApplet {







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

public void setup()
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

public void draw()
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

public void initGui()
{
  ctrl = new ControlP5(this);
  Toggle head = ctrl.addToggle("tglHead",960,30, 15,15);
  Toggle neck = ctrl.addToggle("tglNeck",960,90, 15,15);
  Toggle leftShoulder = ctrl.addToggle("tglLeftShoulder", 900,90,15,15);
  Toggle rightShoulder = ctrl.addToggle("tglRightShoulder", 1020,90,15,15);
  Toggle spine = ctrl.addToggle("tglSpine", 960, 175, 15,15);
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

public void drawPanelLines()
{
  stroke(255,0,0);
  strokeWeight(3);
  line(968, 45, 968,90);//Head to neck
  line(915, 98, 960,98);//Left shoulder to neck
  line(975,98,1020,98);//Neck to right shoulder
  line(908, 105, 893,180);//Left shoulder to left elbow
  line(893,195, 883,260);//Left elbow to left hand
  line(1028,105, 1043,180);//Right shoulder to right elbow
  line(1043,195,1053,260); //Right elbow to right hand
  line(915,105,960,175);//Left shoulder to spine 
  line(1020,105,975,175);//Right shoulder to spine
  line(928, 250,960,190);//Left hip to spine
  line(975,190, 1008,250);//Spine to right hip
  line(935,258,1000,258);
  line(928,265,913,360); //Left hip to left knee
  line(1008,265,1023,360); //Right hip to right knee
  line(913,375, 913,450);//Left knee to left foot
  line(1023,375,1023,450); //Right knee to right foot

}

public void drawPosition(SkeletonData _s) 
{
  noStroke();
  fill(0, 100, 255);
  String s1 = str(_s.dwTrackingID);
  text(s1, _s.position.x*width/2, _s.position.y*height/2);
}

public void drawSkeleton(SkeletonData _s) 
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

public void DrawBone(SkeletonData _s, int _j1, int _j2) 
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

public void appearEvent(SkeletonData _s) 
{
  if (_s.trackingState == PKinect.NUI_SKELETON_NOT_TRACKED) 
  {
    return;
  }
  synchronized(bodies) {
    bodies.add(_s);
  }
}

public void disappearEvent(SkeletonData _s) 
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

public void moveEvent(SkeletonData _b, SkeletonData _a) 
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
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "kinectClient" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
