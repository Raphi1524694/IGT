package IGT;

public class Config {

    public static PERSISTENCE_UNIT DB = PERSISTENCE_UNIT.MONGODB;


    public enum PERSISTENCE_UNIT {
        MYSQL(6001), POSTGRESQL(6003), MONGODB(6002), NEO4J(6004), INFINISPAN(6005), CASSANDRA(6006), REDIS(6007);

        private int port;

        PERSISTENCE_UNIT(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }
        }

}
