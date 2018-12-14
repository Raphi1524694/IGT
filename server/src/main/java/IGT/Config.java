package IGT;

public class Config {

    public static PERSISTENCE_UNIT DB = PERSISTENCE_UNIT.MONGODB;


    public enum PERSISTENCE_UNIT {
        POSTGRES(6001), MONGODB(6002), NEO4J(6003), CASSANDRA(6004), REDIS(6005);

        private int port;

        PERSISTENCE_UNIT(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }
        }

}
