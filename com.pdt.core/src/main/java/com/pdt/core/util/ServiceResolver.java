package com.pdt.core.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceResolver {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceResolver.class);

	private static ServiceResolver instance;
	/**
	 * Service container that contain services that has a key is service interface class name, map with the implemented
	 * service object
	 */
	private Map<String, Object> services = new HashMap<>();

	private ServiceResolver() {
		//singleton
	}

	public static ServiceResolver createServiceResolver(Map<String, Object> registedServices) {
		if (instance != null)
			throw new IllegalStateException(ServiceResolver.class.getName() + " has been instanated");
		ServiceResolver newInstance = new ServiceResolver();
		try {
			for (Entry<String, Object> service : registedServices.entrySet()) {
				Class<?> serviceInterface = Class.forName(service.getKey());
				if (!serviceInterface.isInterface())
					throw new IllegalArgumentException("Wrong service interface was registed:"
							+ serviceInterface.getName() + " is not an interface");
				if (!serviceInterface.isAssignableFrom(service.getValue().getClass()))
					throw new IllegalArgumentException("Class " + service.getValue().getClass().getName()
							+ " is not service that implement " + serviceInterface.getName());
				if (newInstance.services.containsKey(serviceInterface.getName()))
					throw new IllegalStateException("Duplicate interface defined: " + serviceInterface.getName());

				LOGGER.info("Loaded service: " + serviceInterface.getName());
				newInstance.services.put(serviceInterface.getName(), service.getValue());
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return instance = newInstance;
	}

	@SuppressWarnings("unchecked")
	public static <T> T findService(Class<T> serviceInterface) {
		if (serviceInterface == null || !serviceInterface.isInterface())
			return null;

		T service = (T) getInstance().services.get(serviceInterface.getName());
		return service;
	}

	private static ServiceResolver getInstance() {
		if (instance == null) {
			throw new IllegalStateException(ServiceResolver.class.getName() + " was not initialized.");
		}
		return instance;
	}
}
