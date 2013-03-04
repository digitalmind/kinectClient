import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import pKinect.PKinect; 
import pKinect.SkeletonData; 
import oscP5.*; 
import netP5.*; 
import controlP5.*; 
import java.util.*; 

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

Vector<SkeletonData> bodies;
OscP5 oscP5;
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
  bodies = new Vector<SkeletonData>();
	smooth();
  oscP5 = new OscP5(this, 12000);
	remoteIp = new NetAddress("127.0.0.1",12002);
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
  for(int i=0;i<bodies.size();i++)
  {
    SkeletonData s = bodies.get(i);
    drawSkeleton(s); 
    displayValues(s);
    sendMessage(s, i);
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
  line(935,258,1000,258); //Left hip to right hip
  line(928,265,913,360); //Left hip to left knee
  line(1008,265,1023,360); //Right hip to right knee
  line(913,375, 913,450);//Left knee to left foot
  line(1023,375,1023,450); //Right knee to right foot

}

public void displayValues(SkeletonData _s)
{
  PVector[] jLocs = new PVector[15];
  
  jLocs[0] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HEAD);
  jLocs[1] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SHOULDER_CENTER);
  jLocs[2] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SHOULDER_LEFT);
  jLocs[3] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SHOULDER_RIGHT);
  jLocs[4] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SPINE);
  jLocs[5] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_ELBOW_LEFT);
  jLocs[6] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_ELBOW_RIGHT);
  jLocs[7] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HAND_LEFT);
  jLocs[8] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HAND_RIGHT);
  jLocs[9] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HIP_LEFT);
  jLocs[10] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HIP_RIGHT);
  jLocs[11] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_KNEE_LEFT);
  jLocs[12] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_KNEE_RIGHT);
  jLocs[13] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_FOOT_LEFT);
  jLocs[14] = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_FOOT_RIGHT);

  if(tglHead)
  {
    fill(200);
    text("Head: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[0].z+"]", 650,10);
  }
  else
  {
    fill(80);
    text("Head: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[0].z+"]", 650,10);
  }

  if(tglNeck)
  {
    fill(200);
    text("Neck: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[1].z+"]", 650,25);
  }
  else
  {
    fill(80);
    text("Neck: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[1].z+"]", 650,25);
  }

  if(tglLeftShoulder)
  { 
    fill(200);
    text("L-Shoulder: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[2].z+"]", 650,40);
  }
  else
  {
    fill(80);
    text("L-Shoulder: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[2].z+"]", 650,40);
  }

  if(tglRightShoulder)
  {
    fill(200);
    text("R-Shoulder: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[3].z+"]", 650,55);
  }
  else
  {
    fill(80);
    text("R-Shoulder: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[3].z+"]", 650,55);
  }

  if(tglLeftElbow)
  {
    fill(200);
    text("L-Elbow: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[4].z+"]", 650,70);
  }
  else
  {
    fill(80);
    text("L-Elbow: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[4].z+"]", 650,70);
  }
  if(tglRightElbow)
  {
    fill(200);
    text("R-Elbow: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[5].z+"]", 650,85);
  }
  else
  {
    fill(80);
    text("R-Elbow: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[5].z+"]", 650,85);
  }

  if(tglLeftHand)
  {
    fill(200);
    text("L-Hand: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[6].z+"]", 650,100);
  }
  else
  {
    fill(80);
    text("L-Hand: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[6].z+"]", 650,100);
  }
  if(tglRightHand)
  {
    fill(200);
    text("R-Hand: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[7].z+"]", 650,115);
  }
  else
  {
    fill(80);
    text("R-Hand: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[7].z+"]", 650,115);
  }

  if(tglSpine)
  {
    fill(200);
    text("Spine: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[8].z+"]", 650,130);
  }
  else
  {
    fill(80);
    text("Spine: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[8].z+"]", 650,130);
  }
  if(tglLeftHip)
  {
    fill(200);
    text("L-Hip: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[9].z+"]", 650,145);
  }
  else
  {
    fill(80);
    text("L-Hip: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[9].z+"]", 650,145);
  }

  if(tglRightHip)
  {
    fill(200);
    text("R-Hip: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[10].z+"]", 650,160);
  }
  else
  {
    fill(80);
    text("R-Hip: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[10].z+"]", 650,160);
  }

  if(tglLeftKnee)
  {
    fill(200);
    text("L-Knee: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[11].z+"]", 650,175);
  }
  else
  {
    fill(80);
    text("L-Knee: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[11].z+"]", 650,175);
  }

  if(tglRightKnee)
  {
    fill(200);
    text("R-Knee: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[12].z+"]", 650,190);
  }
  else
  {
    fill(80);
    text("R-Knee: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[12].z+"]", 650,190);
  }

  if(tglLeftFoot)
  {
    fill(200);
    text("L-Foot: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[13].z+"]", 650,205);
  }
  else
  {
    fill(80);
    text("L-Foot: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[13].z+"]", 650,205);
  }

  if(tglRightFoot)
  {
    fill(200);
    text("R-Foot: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[14].z+"]", 650,220);
  }
  else
  {
    fill(80);
    text("R-Foot: ["+jLocs[0].x*width/2+","+jLocs[0].y*height+","+jLocs[14].z+"]", 650,220);
  }
}

public void sendMessage(SkeletonData _s, int _index)
{
  if(tglHead)
  {
      OscMessage myMessage = new OscMessage("/head");
      myMessage.add(_index);
      PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HEAD);
      myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
      oscP5.send(myMessage, remoteIp); 

  }
  if(tglNeck)
  {
    OscMessage myMessage = new OscMessage("/neck");  
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SHOULDER_CENTER);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglLeftShoulder)
  {
    OscMessage myMessage = new OscMessage("/lShoulder");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SHOULDER_LEFT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglRightShoulder)
  {
    OscMessage myMessage = new OscMessage("/rShoulder");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SHOULDER_RIGHT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglSpine){
    OscMessage myMessage = new OscMessage("/spine");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_SPINE);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglLeftElbow)
  {
    OscMessage myMessage = new OscMessage("/lElbow");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_ELBOW_LEFT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglRightElbow)
  {
    OscMessage myMessage = new OscMessage("/rElbow");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_ELBOW_RIGHT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglLeftHand)
  {
    OscMessage myMessage = new OscMessage("/lHand");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HAND_LEFT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglRightHand){
    OscMessage myMessage = new OscMessage("/rHand");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HAND_RIGHT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglLeftHip)
  {
    OscMessage myMessage = new OscMessage("/lHip");
    myMessage.add(_index);
    PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HIP_LEFT);
    myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
    oscP5.send(myMessage, remoteIp); 

  }
  if(tglRightHip)
  {
      OscMessage myMessage = new OscMessage("/rHip");
      myMessage.add(_index);
      PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_HIP_RIGHT);
      myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
      oscP5.send(myMessage, remoteIp); 

  }
  if(tglLeftKnee)
  { 
      OscMessage myMessage = new OscMessage("/lKnee");
      myMessage.add(_index);
      PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_KNEE_RIGHT);
      myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
      oscP5.send(myMessage, remoteIp); 
    
  }
  if(tglRightKnee)
  {
      OscMessage myMessage = new OscMessage("/rKnee");
      myMessage.add(_index);
      PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_KNEE_RIGHT);
      myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
      oscP5.send(myMessage, remoteIp); 

  }
  if(tglLeftFoot)
  {
      OscMessage myMessage = new OscMessage("/lFoot");
      myMessage.add(_index);
      PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_FOOT_LEFT);
      myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
      oscP5.send(myMessage, remoteIp); 

  }
  if(tglRightFoot)
  {
      OscMessage myMessage = new OscMessage("/rFoot");
      myMessage.add(_index);
      PVector jPos = getJointPosition(_s, PKinect.NUI_SKELETON_POSITION_FOOT_RIGHT);
      myMessage.add(new float[] {jPos.x, jPos.y, jPos.z});
      oscP5.send(myMessage, remoteIp); 
  }

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
public PVector getJointPosition(SkeletonData _s, int j)
{
  PVector pos;
  if(_s.skeletonPositionTrackingState[j] != PKinect.NUI_SKELETON_POSITION_NOT_TRACKED)
  {
    pos = new PVector((float)_s.skeletonPositions[j].x,(float)_s.skeletonPositions[j].y,(float) _s.skeletonPositions[j].z);
  }
  else  {
   pos = new PVector(-1.0f,-1.0f,-1.0f);
  }
  return pos;
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
    println("Skeleton "+_s.dwTrackingID+" is added.");
  }
}

public void disappearEvent(SkeletonData _s) 
{
  println("and "+_s.dwTrackingID+" is gone");  
  synchronized(bodies) 
  {
  for (int i=bodies.size()-1; i>=0; i--) 
  {
      if (_s.dwTrackingID == bodies.get(i).dwTrackingID || bodies.get(i).dwTrackingID == 0) 
      {
        bodies.remove(i);
        println("body #"+_s.dwTrackingID+" is removed.");
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
