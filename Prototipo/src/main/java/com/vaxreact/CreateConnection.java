package com.vaxreact;

public class CreateConnection {
    private static DataBaseConnection obj;

    // private constructor to force use of
    // getInstance() to create Singleton object
    private CreateConnection() {}

    public static DataBaseConnection getInstance()
    {
        if (obj==null)
            obj = new DataBaseConnection();
        return obj;
    }
}
