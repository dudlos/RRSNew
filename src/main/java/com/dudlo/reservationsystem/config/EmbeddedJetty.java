package com.dudlo.reservationsystem.config;




public class EmbeddedJetty {

//    private static final Logger logger = LoggerFactory.getLogger(EmbeddedJetty.class);
//    private static final int DEFAULT_PORT = 8080;
//    private static final String CONTEXT_PATH = "/";
//	private static final String CONFIG_LOCATION = "com.dudlo.reservationsystem";
//	private static final String MAPPING_URL = "/*";
//    //private static final String DEFAULT_PROFILE = "dev";
//	@Autowired
//	ContextInitializer initializer;
//
//    public static void main(String[] args) throws Exception {
//       // new EmbeddedJetty().startJetty(getPortFromArgs(args));
//    }
//
//    private static int getPortFromArgs(String[] args) {
//        if (args.length > 0) {
//            try {
//                return Integer.valueOf(args[0]);
//            } catch (NumberFormatException ignore) {
//            }
//        }
//        logger.debug("No server port configured, falling back to {}", DEFAULT_PORT);
//        return DEFAULT_PORT;
//    }
//
//    private void startJetty(int port) throws Exception {
//        logger.debug("Starting server at port {}", port);
//        Server server = new Server(port);
//        server.start();
//        logger.info("Server started at port {}", port);
//        server.join();
//    }

//    private static ServletContextHandler getServletContextHandler(WebApplicationContext context) throws IOException {
//        ServletContextHandler contextHandler = new ServletContextHandler();
//        contextHandler.setErrorHandler(null);
//        contextHandler.setContextPath(CONTEXT_PATH);
//        contextHandler.addServlet(new ServletHolder(new DispatcherServlet(context)), MAPPING_URL);
//        contextHandler.addEventListener(new ContextLoaderListener(context));
//        contextHandler.setResourceBase(new ClassPathResource("webapp").getURI().toString());
//        return contextHandler;
//    }
//
//    private static WebApplicationContext getContext() {
//        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
//        context.setConfigLocation(CONFIG_LOCATION);
//        //context.getEnvironment().setDefaultProfiles(DEFAULT_PROFILE);
//        return context;
//    }

}