DROP TABLE tilegis.tile_highway;
DROP TABLE tilegis.node_tag;
DROP TABLE tilegis.way_node;
DROP TABLE tilegis.way_tag;
DROP TABLE tilegis.tile_natural;
DROP TABLE tilegis.tile_railway;
DROP TABLE tilegis.tile_leisure;
DROP TABLE tilegis.tile_waterway;
DROP TABLE tilegis.tile_landuse;
DROP TABLE tilegis.highway;
DROP TABLE tilegis.waterway;
DROP TABLE tilegis.natural;
DROP TABLE tilegis.leisure;
DROP TABLE tilegis.railway;
DROP TABLE tilegis.landuse;
DROP TABLE tilegis.tile;
DROP TABLE tilegis.node;
DROP TABLE tilegis.way;

CREATE TABLE tilegis.way (
       id BIGINT NOT NULL
     , PRIMARY KEY (id)
);

CREATE TABLE tilegis.node (
       id BIGINT NOT NULL
     , latitude REAL
     , longitude REAL
     , PRIMARY KEY (id)
);

CREATE TABLE tilegis.tile (
       x INTEGER NOT NULL
     , y INTEGER NOT NULL
     , PRIMARY KEY (x, y)
);

CREATE TABLE tilegis.landuse (
       id BIGINT NOT NULL
     , nature VARCHAR(255) NOT NULL
     , name VARCHAR(255)
     , PRIMARY KEY (id)
     , CONSTRAINT FK_landuse_1 FOREIGN KEY (id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.railway (
       id BIGINT NOT NULL
     , nature VARCHAR(255) NOT NULL
     , name VARCHAR(255)
     , PRIMARY KEY (id)
     , CONSTRAINT FK_railway_1 FOREIGN KEY (id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.leisure (
       id BIGINT NOT NULL
     , nature VARCHAR(255) NOT NULL
     , name VARCHAR(255)
     , PRIMARY KEY (id)
     , CONSTRAINT FK_leisure_1 FOREIGN KEY (id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.natural (
       id BIGINT NOT NULL
     , nature VARCHAR(255) NOT NULL
     , name VARCHAR(255)
     , PRIMARY KEY (id)
     , CONSTRAINT FK_natural_1 FOREIGN KEY (id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.waterway (
       id BIGINT NOT NULL
     , nature VARCHAR(255) NOT NULL
     , name VARCHAR(255)
     , PRIMARY KEY (id)
     , CONSTRAINT FK_waterway_1 FOREIGN KEY (id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.highway (
       id BIGINT NOT NULL
     , nature VARCHAR(255) NOT NULL
     , name VARCHAR(255)
     , oneway BOOLEAN
     , PRIMARY KEY (id)
     , CONSTRAINT FK_highway_1 FOREIGN KEY (id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.tile_landuse (
       landuse_id BIGINT NOT NULL
     , y INTEGER NOT NULL
     , x INTEGER NOT NULL
     , PRIMARY KEY (landuse_id, y, x)
     , CONSTRAINT FK_tile_landuse_1 FOREIGN KEY (x, y)
                  REFERENCES tilegis.tile (x, y)
     , CONSTRAINT FK_tile_landuse_2 FOREIGN KEY (landuse_id)
                  REFERENCES tilegis.landuse (id)
);

CREATE TABLE tilegis.tile_waterway (
       waterway_id BIGINT NOT NULL
     , y INTEGER NOT NULL
     , x INTEGER NOT NULL
     , PRIMARY KEY (waterway_id, y, x)
     , CONSTRAINT FK_tile_waterway_1 FOREIGN KEY (x, y)
                  REFERENCES tilegis.tile (x, y)
     , CONSTRAINT FK_tile_waterway_2 FOREIGN KEY (waterway_id)
                  REFERENCES tilegis.waterway (id)
);

CREATE TABLE tilegis.tile_leisure (
       leisure_id BIGINT NOT NULL
     , y INTEGER NOT NULL
     , x INTEGER NOT NULL
     , PRIMARY KEY (leisure_id, y, x)
     , CONSTRAINT FK_tile_leisure_1 FOREIGN KEY (x, y)
                  REFERENCES tilegis.tile (x, y)
     , CONSTRAINT FK_tile_leisure_2 FOREIGN KEY (leisure_id)
                  REFERENCES tilegis.leisure (id)
);

CREATE TABLE tilegis.tile_railway (
       railway_id BIGINT NOT NULL
     , y INTEGER NOT NULL
     , x INTEGER NOT NULL
     , PRIMARY KEY (railway_id, y, x)
     , CONSTRAINT FK_tile_railway_1 FOREIGN KEY (x, y)
                  REFERENCES tilegis.tile (x, y)
     , CONSTRAINT FK_tile_railway_2 FOREIGN KEY (railway_id)
                  REFERENCES tilegis.railway (id)
);

CREATE TABLE tilegis.tile_natural (
       natural_id BIGINT NOT NULL
     , y INTEGER NOT NULL
     , x INTEGER NOT NULL
     , PRIMARY KEY (natural_id, y, x)
     , CONSTRAINT FK_tile_natural_1_2 FOREIGN KEY (natural_id)
                  REFERENCES tilegis.natural (id)
     , CONSTRAINT FK_tile_natural_1_1 FOREIGN KEY (x, y)
                  REFERENCES tilegis.tile (x, y)
);

CREATE TABLE tilegis.way_tag (
       id BIGINT NOT NULL
     , way_id BIGINT NOT NULL
     , key VARCHAR(255)
     , value VARCHAR(255)
     , PRIMARY KEY (id, way_id)
     , CONSTRAINT FK_way_tag_1 FOREIGN KEY (way_id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.way_node (
       node_id BIGINT NOT NULL
     , way_id BIGINT NOT NULL
     , PRIMARY KEY (node_id, way_id)
     , CONSTRAINT FK_way_node_1 FOREIGN KEY (node_id)
                  REFERENCES tilegis.node (id)
     , CONSTRAINT FK_way_node_2 FOREIGN KEY (way_id)
                  REFERENCES tilegis.way (id)
);

CREATE TABLE tilegis.node_tag (
       id BIGINT NOT NULL
     , node_id BIGINT NOT NULL
     , key VARCHAR(255)
     , value VARCHAR(255)
     , PRIMARY KEY (id, node_id)
     , CONSTRAINT FK_node_tag_1 FOREIGN KEY (node_id)
                  REFERENCES tilegis.node (id)
);

CREATE TABLE tilegis.tile_highway (
       highway_id BIGINT NOT NULL
     , y INTEGER NOT NULL
     , x INTEGER NOT NULL
     , PRIMARY KEY (highway_id, y, x)
     , CONSTRAINT FK_tile_highway_1 FOREIGN KEY (highway_id)
                  REFERENCES tilegis.highway (id)
     , CONSTRAINT FK_tile_highway_2 FOREIGN KEY (x, y)
                  REFERENCES tilegis.tile (x, y)
);

