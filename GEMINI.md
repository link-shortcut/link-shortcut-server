# GEMINI.md

This file provides guidance to Gemini CLI when working with code in this repository.

## Project Overview

This is a URL shortening service called "Link Shortcut" (lnsc.me) - a Korean language project that allows users to create short URLs with expiration dates, immediate expiration via keys, and URL information lookup.

## Key Features

- **URL Shortening**: Creates short URLs with custom expiration dates (valid until midnight of the specified date)
- **Immediate Expiration**: Uses expiration keys to immediately invalidate active short URLs
- **URL Information**: View details by appending `/info` to any valid short URL
- **Traffic Analytics**: Shows daily and total access counts

## Architecture Evolution

The project has evolved through two main architecture versions:

### V1 Architecture
- Single server handling all functionality
- Java Random module for short URL path generation
- Simple monolithic structure

### V2 Architecture (Current)
- Separated short URL path generation into dedicated servers
- Zookeeper-based path allocation system to prevent collisions
- More scalable design supporting ~1 trillion unique paths
- Cache layer for performance optimization (~29% response time improvement)
- Asynchronous access logging

## Repository Structure

- `README.md`: Main project documentation (Korean)
- `assets/`: Architecture diagrams and UI screenshots
- `profile/`: Additional project documentation

## Development Context

This appears to be a documentation-only repository containing project overview, architecture diagrams, and service descriptions. The actual source code is likely hosted elsewhere as no code files were found in this repository.

The project demonstrates concepts in:
- Distributed systems design
- Database optimization
- Caching strategies
- Performance testing
- System architecture evolution

## Code Review Guidelines

When reviewing code for this project, please focus on:

1. **Scalability**: Ensure designs support high traffic and concurrent users
2. **Performance**: Evaluate caching strategies and database query optimization
3. **Distributed Systems**: Check for proper handling of distributed system challenges (race conditions, network failures, etc.)
4. **URL Generation**: Verify collision prevention and uniqueness guarantees
5. **Expiration Logic**: Ensure time-based expiration is handled correctly across timezones
6. **Korean Language Support**: Verify proper UTF-8 handling and Korean text display