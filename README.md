Project Web Crawler
# Cochrane Library Review Crawler

## Overview

The Cochrane Library Review Crawler is a Java-based application designed to fetch and store URLs and associated metadata from the Cochrane Library review collection. This application provides a searchable index of a medical school's curriculum content (files, videos, events, etc.) and allows for broader searches including resources from the Cochrane Library.

## Project Features

- Fetches review URLs and metadata for specified medical topics from the Cochrane Library.
- Writes the collected data to a text file.
- Handles URL encoding and HTML entity decoding to ensure accurate data retrieval.

## Technologies Used

- **Java 8**
- **Spring Boot 2.5.4**
- **Jsoup 1.14.3**: For HTML parsing.
- **Apache Commons Lang 3.12.0**: For handling HTML entity decoding.
- **Maven**: For project management and dependency management.

## Requirements

- JDK 1.8+
- Maven 3.6+
- Internet connection for fetching data from the Cochrane Library.

## Project Structure

- `src/main/java/com/vantage/crawler/controller/CochraneCrawlerController.java`: REST controller for handling API requests.
- `src/main/java/com/vantage/crawler/service/CochraneCrawlerService.java`: Service class for fetching and processing review data.
- `src/main/resources/application.properties`: Spring Boot configuration file.

## Setup Instructions

### Prerequisites

Ensure you have the following installed:

- Java JDK 1.8+
- Maven 3.6+

### Clone the Repository

```bash
git clone https://github.com/your-username/cochrane-library-review-crawler.git
cd cochrane-library-review-crawler
Build the Project
bash
Copy code
mvn clean install
Run the Application
bash
Copy code
mvn spring-boot:run
Usage
Fetch Reviews for Predefined Topics
To fetch reviews for predefined topics (e.g., "Allergy & intolerance"):





