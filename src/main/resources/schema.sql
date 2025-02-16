-- EXODUS_IAM.application definition

CREATE TABLE `application` (
  `id` bigint unsigned NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE EXODUS_IAM.application ADD CONSTRAINT application_unique UNIQUE KEY (name);

-- EXODUS_IAM.application_seq definition

CREATE TABLE `application_seq` (
  `next_val` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- EXODUS_IAM.auth_point_type definition

CREATE TABLE `auth_point_type` (
  `id` bigint unsigned NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `require_value` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE EXODUS_IAM.auth_point_type ADD CONSTRAINT auth_point_type_unique UNIQUE KEY (name);

-- EXODUS_IAM.auth_point_type_seq definition

CREATE TABLE `auth_point_type_seq` (
  `next_val` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- EXODUS_IAM.`identity` definition

CREATE TABLE `identity` (
  `id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- EXODUS_IAM.identity_seq definition

CREATE TABLE `identity_seq` (
  `next_val` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- EXODUS_IAM.identity_auth_point definition

CREATE TABLE `identity_auth_point` (
  `id` bigint unsigned NOT NULL,
  `application_id` bigint unsigned NOT NULL,
  `auth_point_type_id` bigint unsigned NOT NULL,
  `identity_id` bigint unsigned NOT NULL,
  `auth_point_value` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbd6dixuo877osxghgqx7xxf8k` (`application_id`),
  KEY `FKckn6ce2hxc5pjjmkkau51uckq` (`auth_point_type_id`),
  KEY `FKo4nhfrr4n2ubqjr31fjb5hqbd` (`identity_id`),
  CONSTRAINT `FKbd6dixuo877osxghgqx7xxf8k` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FKckn6ce2hxc5pjjmkkau51uckq` FOREIGN KEY (`auth_point_type_id`) REFERENCES `auth_point_type` (`id`),
  CONSTRAINT `FKo4nhfrr4n2ubqjr31fjb5hqbd` FOREIGN KEY (`identity_id`) REFERENCES `identity` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- EXODUS_IAM.identity_auth_point_seq definition

CREATE TABLE `identity_auth_point_seq` (
  `next_val` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE EXODUS_IAM.identity_auth_point ADD CONSTRAINT identity_auth_point_unique_per_identity UNIQUE KEY (application_id,identity_id,auth_point_type_id);

-- EXODUS_IAM.tag_type definition

CREATE TABLE `tag_type` (
  `id` bigint unsigned NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- EXODUS_IAM.tag_type_seq definition

CREATE TABLE `tag_type_seq` (
  `next_val` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- EXODUS_IAM.identity_tag definition

CREATE TABLE `identity_tag` (
  `id` bigint unsigned NOT NULL,
  `application_id` bigint unsigned NOT NULL,
  `identity_id` bigint unsigned NOT NULL,
  `tag_type_id` bigint unsigned NOT NULL,
  `tag_value` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK6d3wkecpc313oo3v44yuevyf5` (`application_id`),
  KEY `FKjgm59soin0wm02gg9j0m3gmjv` (`identity_id`),
  KEY `FKtojgjbjod4ls7dtxywwao8mey` (`tag_type_id`),
  CONSTRAINT `FK6d3wkecpc313oo3v44yuevyf5` FOREIGN KEY (`application_id`) REFERENCES `application` (`id`),
  CONSTRAINT `FKjgm59soin0wm02gg9j0m3gmjv` FOREIGN KEY (`identity_id`) REFERENCES `identity` (`id`),
  CONSTRAINT `FKtojgjbjod4ls7dtxywwao8mey` FOREIGN KEY (`tag_type_id`) REFERENCES `tag_type` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
ALTER TABLE EXODUS_IAM.identity_tag ADD CONSTRAINT identity_tag_unique_per_identity UNIQUE KEY (application_id,identity_id,tag_type_id);
ALTER TABLE EXODUS_IAM.identity_tag ADD CONSTRAINT identity_tag_unique_per_application UNIQUE KEY (application_id,tag_type_id,tag_value);

-- EXODUS_IAM.identity_tag_seq definition

CREATE TABLE `identity_tag_seq` (
  `next_val` bigint unsigned DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
