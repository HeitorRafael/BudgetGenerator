package api;

public @interface WebServlet {

    String value();

    String name();

    String[] urlPatterns();

}
