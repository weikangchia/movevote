# MoveVote
RECOMMEND MOVIE TO YOU AND YOUR FRIENDS

Orbital 2016<br>
Team: Civil War<br>
Member: David, Wei Kang<br>
Level of achievement: Gemini

##Overview
Given so many choices today, many of us tends to have a hard time making decisions and it is almost impossible to make without additional information or guidance. Furthermore many research have demonstrated that users’ marginal utility decreases when confronted with too many choices. Our aims for this summer project is to help people make better choices about which movies to watch.

We proposed to build a collective intelligence web application that can smartly predict and recommend movies that you and your friends will like. MoveVote is definitely very useful if you are planning to watch movies with a group of friends or with your loved ones.

Our team will employ a hybrid approach (both content-based filtering and collaborative filtering) to assist our users in their decision-making process. As content-based filtering and collaborative filtering have their own advantages and disadvantages, the hybrid approach will provide a promising extension. We will be leveraging on The Movie Database (TMDB) for pulling movie content information.

MoveVote will be built and hosted on Google App Engine, a platform for building scalable web applications and mobile backends. We have chosen picked this platform as it provides many built-in services and APIs such as DataStore, Google Identity Toolkit and Task Scheduling. In addition, Google App Engine allows us to remove the time spent on maintaining our own server and spend more time to innovate on our project solution. In terms of security aspect, Google App Engine provides an automated security scanner to scan and detect common web application vulnerabilities.

## Features
* Personalized Movie Recommendation - Sign up for MoveVote and by rating some movies, we can help you get more movies that you and/or your group of friends will like.
* Latest Movie Showing Time - Not sure when and where your latest movie will be showing today and tomorrow? MoveVote can help you get all these information for you.
* Modern Responsive Design - To provide our users an optimal viewing and interaction experience, we have adopted a modern responsive framework based on Material Design by Google.
* Support multiple login options - Email, Google and Facebook (will implement more if we have more time)
* Cinema location - By leveraging on Google Maps, we can display a map to show them the direction to the Cinema that they want to go. (Future enhancement)

## User Stories
1. As a user, I want to have all the information about the latest movies that are currently showing in our local cinema so that I do not have to check their websites individually.
2. As a user, I want to know what other similar movies to the movie that I am currently watching.
3. As a user, I want to know what are the movies that my friend and I would like to watch so that we can catch the movie together.
4. As an administrator, I want to be able to update our movie database every day in order to ensure that the users get reliable and up-to-date information.

For milestone 1, we have finished designing most of the user interface and have deployed a alpha version to Google App Engine. At this stage, users are able to register and login via using their email, Google and Facebook account. In addition, users are also able to see what are the current movies that are showing in our local cinema.

For milestone 2, we have completed the registration and login process. In this update, user get to see more information (overview, now showing, similar movies and trailer) about the movies that are currently showing in the cinema. We have also implemented the movie survey to get to know the user movie preferences.

For milestone 3, we have completed the recommendation system for individual. Due to time constraint, we are unable to implement the recommendation system for group and we will be leaving it for future enhancement. We have used Google PageSpeed tools to analyze and optimize our web app. Before optimizing, we have scored 52/100 (mobile), 86/100 (user experience) and 47/100 (desktop). After fixing the problems highlighted by Google PageSpeed tools, we have managed to impoved the score, 77/100 (mobile), 96/100 (user experience) and 92/100 (desktop).

## Project Log

| Description | David | Wei Kang |
| --- | :---: | :---: |
| Liftoff Day 1 | 8 | 8 |
| Liftoff Day 2 | 8 | 8 |
| Project brainstorm and planning | 3 | 3 |
| Install IDE and tools | 2 | 2 |
| Design user interface | - | 23 |
| Explore Single Sign-On (SSO) platform | 4 | 2 |
| Implement Google Identity Toolkit | - | 24 |
| Explore TMDB movie database api | - | 2 |
| Preparation for milestone 1 | 0.5 | 1.5 |
| Preparation for milestone 2 | 1 | 1.5 |
| Implement movie rating function | - | 12 |
| Implement movie showtimes function | - | 16 |
| Create a cron job to grab information about the movies that are currently showing in Singapore's cinema | - | 8 |
| Implement movie survey | - | 8 |
| Design Recommendation system | 10 | - |
| Implement add and remove friend | - | 4 |
| Improve page loading time | - | 2 |
| Conduct user testing | - | 1 |
| Others | - | 4 |
| Bug fix | - | 8 |
| **Total:** | 36.5 | 138 |

We have finished 36.5 and 138 hours respectively towards the requirement.

## Mission
- [ ] Attend at least 12 hours of workshops
- [X] Use a web application with CRUD capabilities
- [X] 4 additional extension milestones.
- [ ] Score a minimum of 2 stars on feedback given to other teams
- [ ] Must score a minimum of 3 stars on own peer-graded project

## Milestones
### Milestone 1
- [X] Create accounts and install IDE and tools
- [X] Design prototype (complete most of the prototype)
- [X] Implement Google Identity Toolkit (able to login and register)

### Milestone 2
- [X] Implement Google Identity Toolkit (send email for verification and change password)
- [X] Implement movie showtimes
- [X] Implement movie rating
- [X] Design recommendation engine

### Milestone 3
- [X] Add and remove friends
- [X] Implement recommendation engine (individual)
- [X] Conduct user testing
- [X] Gather feedback on how to improve the user experience
- [X] Improve performance

## References
### Learning Resources
* [StackOverflow](http://stackoverflow.com/) - any questions

### Development
* [Materialize](http://materializecss.com/) - a modern responsive CSS framework based on Material Design by Google
* [The Movie Database (TMDb)](https://www.themoviedb.org/?language=en) - a popular, user editable database for movies and TV shows.
* [Google App Engine](https://appengine.google.com/) - a platform for building scalable web applications and mobile backends.
* [jQuery](https://jquery.com/) - a write less, do more, JavaScript library.
* [Google Identity Toolkit](https://developers.google.com/identity/toolkit/) - a robust, more secure authentication system that helps you do sign-in the right way, and can grow with your app.
* [Google Identity Toolkit Java Client](https://github.com/google/identity-toolkit-java-client) - a very good Java library for google Idenity Toolkit services.
* [Google cron.xml Reference](https://cloud.google.com/appengine/docs/java/config/cronref) - provides details on the options for cron job in Google App Engine
* [Material Palette](http://www.materialpalette.com/) - to get some reference to generate some colors
* [jQuery Star Rating plugin](https://github.com/jvv/jquery-star-rating-plugin) - a simple star rating plugin which uses Googles Materialize Icons.

### Software used
* PowerPoint - to prepare slides
* Adobe After Effects CS5 - to render videos
* Adobe Photoshop CS5 - to create some wonderful images
* Eclipse - IDE

## Website
Link: [https://movevote.appspot.com](https://movevote.appspot.com)

## Video
* Ignition @Hanger: [YouTube Link](https://youtu.be/hK8Z0QLRlbU?t=30m45s)
* Intro Video: [YouTube Link](https://youtu.be/BHlYEgWAAVw)
* Milestone 2 Video: [YouTube Link](https://www.youtube.com/watch?v=CrXkAi_YqXI)
