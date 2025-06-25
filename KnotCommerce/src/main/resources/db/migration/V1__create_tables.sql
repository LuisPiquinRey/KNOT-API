
CREATE TABLE `category` (
    `id_category` int NOT NULL AUTO_INCREMENT,
    `description` varchar(100) NOT NULL,
    `name` varchar(50) NOT NULL,
    PRIMARY KEY (`id_category`),
    UNIQUE KEY `UK3x7l6yk1oxdxmdh4am3yq2y38` (`description`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
    `product_type` varchar(31) NOT NULL,
    `id_product` bigint NOT NULL AUTO_INCREMENT,
    `available` bit(1) DEFAULT NULL,
    `description` varchar(100) DEFAULT NULL,
    `name` varchar(20) DEFAULT NULL,
    `price` double DEFAULT NULL,
    `stock` int DEFAULT NULL,
    `version` int DEFAULT NULL,
    `warranty_period` varchar(20) DEFAULT NULL,
    `expiration_date` date DEFAULT NULL,
    `recommended_temperature` float DEFAULT NULL,
    PRIMARY KEY (`id_product`),
    UNIQUE KEY `UKq2n3melweyrl5d4rqkg7pq6ra` (`description`),
    CONSTRAINT `product_chk_1` CHECK ((`stock` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product_category` (
    `id_product` bigint NOT NULL,
    `id_category` int NOT NULL,
    KEY `FK4y2ucymplqxycf58myn6abcv2` (`id_category`),
    KEY `FKt4sn9fs5ju7d8mcoporlyhfun` (`id_product`),
    CONSTRAINT `FK4y2ucymplqxycf58myn6abcv2` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`),
    CONSTRAINT `FKt4sn9fs5ju7d8mcoporlyhfun` FOREIGN KEY (`id_product`) REFERENCES `product` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;