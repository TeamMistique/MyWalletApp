-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               10.3.8-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for mywallet
CREATE DATABASE IF NOT EXISTS `mywallet` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `mywallet`;

-- Dumping structure for table mywallet.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `CategoryID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL DEFAULT '0',
  `TypeID` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`CategoryID`),
  KEY `FK_categories_types` (`TypeID`),
  CONSTRAINT `FK_categories_types` FOREIGN KEY (`TypeID`) REFERENCES `types` (`TypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;

-- Dumping data for table mywallet.categories: ~12 rows (approximately)
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` (`CategoryID`, `Name`, `TypeID`) VALUES
	(1, 'Food', 2),
	(2, 'Vehicle', 2),
	(3, 'Salary', 1),
	(4, 'Gifts', 2),
	(5, 'Clothes', 2),
	(6, 'Sport', 2),
	(7, 'Utilities', 2),
	(8, 'Rent', 2),
	(9, 'Holidays', 2),
	(11, 'Going out', 2),
	(12, 'Health', 2),
	(13, 'Other expenses', 2);
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;

-- Dumping structure for table mywallet.transactions
CREATE TABLE IF NOT EXISTS `transactions` (
  `TransactionID` int(11) NOT NULL AUTO_INCREMENT,
  `Amount` double NOT NULL DEFAULT 0,
  `Time` datetime NOT NULL DEFAULT current_timestamp(),
  `WalletID` int(11) NOT NULL,
  `Currency` char(3) NOT NULL,
  `CategoryID` int(11) NOT NULL,
  `Notes` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`TransactionID`),
  KEY `FK_transactions_wallets` (`WalletID`),
  KEY `FK_transactions_categories` (`CategoryID`),
  CONSTRAINT `FK_transactions_categories` FOREIGN KEY (`CategoryID`) REFERENCES `categories` (`CategoryID`),
  CONSTRAINT `FK_transactions_wallets` FOREIGN KEY (`WalletID`) REFERENCES `wallets` (`WalletID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Dumping data for table mywallet.transactions: ~0 rows (approximately)
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;

-- Dumping structure for table mywallet.types
CREATE TABLE IF NOT EXISTS `types` (
  `TypeID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL DEFAULT '0',
  PRIMARY KEY (`TypeID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

-- Dumping data for table mywallet.types: ~3 rows (approximately)
/*!40000 ALTER TABLE `types` DISABLE KEYS */;
INSERT INTO `types` (`TypeID`, `Name`) VALUES
	(1, 'Income'),
	(2, 'Expense'),
	(3, 'Transfer');
/*!40000 ALTER TABLE `types` ENABLE KEYS */;

-- Dumping structure for table mywallet.wallets
CREATE TABLE IF NOT EXISTS `wallets` (
  `WalletID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(50) NOT NULL DEFAULT 'New Wallet',
  `Balance` double NOT NULL DEFAULT 0,
  `DefaultCurrency` char(3) NOT NULL,
  PRIMARY KEY (`WalletID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

-- Dumping data for table mywallet.wallets: ~2 rows (approximately)
/*!40000 ALTER TABLE `wallets` DISABLE KEYS */;
INSERT INTO `wallets` (`WalletID`, `Name`, `Balance`, `DefaultCurrency`) VALUES
	(1, 'Cash', 0, 'USD'),
	(2, 'Debit Card', 0, 'USD');
/*!40000 ALTER TABLE `wallets` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
