package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Constructor;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.jms.JMSException;

import JMS.JMSController;
import database.Database;
import entity.Route;
import entity.StopTime;
import entity.Trip;
import events.EventHandler;

public class Server
{
	HashMap<String, EventHandler> eventHandlers;
	public JMSController controller;

	public Server()
	{
		getProperties();
		createJMSController();
	}

	public void createJMSController()
	{
		try
		{
			controller = new JMSController(eventHandlers);
			controller.createBroker(61616);
			controller.setUp("SERVER");
		} catch (JMSException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public List<StopTime> getRouteData(String id)
	{
		Database.connect();
		List<Object[]> data = Database.runQuery("stop_times",
				"where trip_id = '" + id + "'");
		List<StopTime> stopTimes = new ArrayList<StopTime>();
		for (Object[] obj : data)
		{
			StopTime st = new StopTime();
			st.setTripId((String) obj[0]);
			st.setArriveTime((Time) obj[2]);
			st.setDepartTime((Time) obj[3]);
			st.setStopSequence(Integer.parseInt((String) obj[1]));
			st.setStopId((String) obj[4]);
			System.out.println(st);
			stopTimes.add(st);
		}
		Database.disconnect();
		return stopTimes;
	}

	public List<Route> getAllRoutes()
	{
		Database.connect();
		List<Object[]> data = Database.runQuery("routes", "");
		List<Route> routes = new ArrayList<Route>();
		for (Object[] obj : data)
		{
			Route st = new Route();
			st.setId((String) obj[0]);
			st.setName((String) obj[1]);
			System.out.println(st);
			routes.add(st);
		}
		Database.disconnect();
		return routes;
	}

	public List<Trip> getTrips(String routeID)
	{
		Database.connect();
		List<Object[]> data = Database.runQuery("trips", "where route_id = '"
				+ routeID + "'");
		List<Trip> trips = new ArrayList<Trip>();
		for (Object[] obj : data)
		{
			Trip st = new Trip();
			st.setId((String) obj[0]);
			st.setRouteId((String) obj[1]);
			System.out.println(st);
			trips.add(st);
		}
		Database.disconnect();
		return trips;
	}

	private void getProperties()
	{
		try
		{
			// read CFG and create Properties object
			Properties properties = new Properties();

			BufferedReader eventcfg = new BufferedReader(new FileReader(
					"server-event-handlers.txt"));
			properties.load(eventcfg);

			int num = Integer.parseInt(properties.getProperty("event-num"));
			eventHandlers = new HashMap<String, EventHandler>();
			for (int i = 1; i <= num; i++)
			{
				String classname = properties.getProperty("event-class" + i);
				String eventName = properties.getProperty("event-name" + i);
				Class<?> c = Class.forName(classname);
				Constructor<?> cons = c.getConstructor(Server.class,
						String.class);
				EventHandler handle = (EventHandler) cons.newInstance(this,
						eventName);

				eventHandlers.put(eventName, handle);
			}

		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
