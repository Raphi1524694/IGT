package IGT;

public class Config {

    public static PERSISTENCE_UNIT DB = PERSISTENCE_UNIT.OGM_MYSQL;


    public enum PERSISTENCE_UNIT {
        OGM_MYSQL(6001), OGM_POSTGRESQL(6002), OGM_MONGODB(6003), OGM_NEO4J(6004), OGM_INFINISPAN(6005), OGM_CASSANDRA(6006), OGM_REDIS(6007);

        private int port;

        PERSISTENCE_UNIT(int port) {
            this.port = port;
        }

        public int getPort() {
            return port;
        }
        }

}
