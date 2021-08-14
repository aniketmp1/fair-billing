package com.edstem.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import javax.xml.bind.ValidationException;

import org.springframework.stereotype.Service;

import com.edstem.enums.SessionState;
import com.edstem.model.Session;
import com.edstem.model.User;

@Service
public class BillCalculator {

	private Map<String,List<Session>> sessionMap=new HashMap<>();
	private Set<User> users=new HashSet<>();
	LocalTime firstSessionTime=null;
	LocalTime lastSessionTime=null;
	public Set<User> getUsers()
	{
		return users;
	}
	public void calculate(String filePath) throws IOException
	{
		 File file = new File(filePath);
		  
		  BufferedReader br = new BufferedReader(new FileReader(file));
		  
		  String st;
		  while ((st = br.readLine()) != null) {
//		    System.out.println(st);
		    try {
				validateAndAddToList(st);
			} catch (ValidationException e) {
				System.out.println("Exception in parcing line:"+e.getMessage());
			}
		  }
		  
		  processSession();
	}
	

	public void validateAndAddToList(String st) throws ValidationException {
		String arr[] =st.split(" ");
		if(arr.length!=3)
			throw new ValidationException("Invalid Stream");
	    String timeStr=arr[0];
	    String userId=arr[1];
	    String sessionStr=arr[2];
	    System.out.println("time:"+timeStr+"  userId:"+userId+"   flag:"+sessionStr);
	    LocalTime time=LocalTime.parse(timeStr);
	    
	    if(firstSessionTime==null) 
	    	firstSessionTime=time;
	    lastSessionTime=time;
	    
	    SessionState state=SessionState.valueOf(sessionStr);
	    if(sessionMap.isEmpty() || sessionMap.get(userId)==null)
	    {
	    	List<Session> sessions=new ArrayList<>();
	    	sessions.add(new Session(time,userId,state));
	    	sessionMap.put(userId, sessions);
	    }
	    else
	    {
	    	List<Session> sessionList=sessionMap.get(userId);
	    	sessionList.add(new Session(time, userId, state));
	    }
//	    System.out.println("sessionMap:"+sessionMap);   
	}


	public void processSession(){
		Iterator<String> itr=sessionMap.keySet().iterator();
		while(itr.hasNext())
		{
			String userId=itr.next();
			Stack<Session> sessionStack=new Stack<>();
			int noOfSessions=0;
			long totalSessionTime=0;
			List<Session> sessions=sessionMap.get(userId);
			for(Session session:sessions)
			{
				if(session.getSessionState().equals(SessionState.Start))
				{
					sessionStack.push(session);
				}
				else
				{
					if(sessionStack.isEmpty())
					{
						sessionStack.push(new Session(firstSessionTime,userId,SessionState.Start));
					}
					Session startSesion=sessionStack.pop();
					long timeDiff=Duration.between(startSesion.getSessionTime(),session.getSessionTime()).toMillis();
					totalSessionTime+=timeDiff/1000;
					noOfSessions++;
				}
			}
			while(!sessionStack.isEmpty())
			{
				Session startSession=sessionStack.pop();
				Session endSession=new Session(lastSessionTime,userId,SessionState.End);
				long timeDiff=Duration.between(startSession.getSessionTime(),endSession.getSessionTime()).toMillis();
				totalSessionTime+=timeDiff/1000;
				noOfSessions++;
			}
			User user=new User(userId, noOfSessions, totalSessionTime);
			users.add(user);
			System.out.println(user.getUserId()+"  "+user.getNoOfSessions()+"  "+user.getTimeInSecs());
		}
	}
}
