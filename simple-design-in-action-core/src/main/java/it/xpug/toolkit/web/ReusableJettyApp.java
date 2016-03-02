package it.xpug.toolkit.web;


import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.mortbay.jetty.*;
import org.mortbay.jetty.handler.*;
import org.mortbay.jetty.servlet.*;
import org.mortbay.resource.Resource;

public class ReusableJettyApp {

	private Server server;
	private final HttpServlet servlet;

	public ReusableJettyApp(HttpServlet servlet) {
		this.servlet = servlet;
	}

	public void start(int port, String resourceBase) {
		server = new Server(port);
		try {
			ResourceHandler resourceHandler = new ResourceHandler();
			HandlerList handlers = new HandlerList();
			handlers.setHandlers(new Handler[] {
				new StaticFilesHandler(Resource.newClassPathResource(resourceBase)),
				servletHandler(),
				new DefaultHandler()
			});
			server.setHandler(handlers);
			server.start();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void shutdown() throws InterruptedException {
		try {
			server.stop();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected ServletHandler servletHandler() {
		ServletHandler servletHandler = new ServletHandler();
		servletHandler.addServletWithMapping(new ServletHolder(servlet), "/");
		return servletHandler;
	}

	private final class StaticFilesHandler extends ResourceHandler {
		private StaticFilesHandler(Resource resourceBase) {
			setBaseResource(resourceBase);
	        setWelcomeFiles(new String[]{ "index.html" });
		}

		@Override
		public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException, ServletException {
			if (request.getPathInfo().equals("/") && !welcomeExists()) {
				((Request) request).setHandled(false);
			} else {
				super.handle(target, request, response, dispatch);
			}
		}

		private boolean welcomeExists() throws IOException {
			return this.getBaseResource().addPath("/index.html").exists();
		}
	}
}
