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
* Cinema location - By leveraging on Google Maps, we can display a map to show them the direction to the Cinema that they want to go.

## User Stories
1. As a user, I want to have all the information about the latest movies that are currently showing in our local cinema so that I do not have to check their websites individually.
2. As a user, I want to know what other similar movies to the movie that I am currently watching.
3. As a user, I want to know what are the movies that my friend and I would like to watch so that we can catch the movie together.
4. As a user, I want to know how to go to the Cinema, so that I do not need to open another app just to find the location.
5. As an administrator, I want to be able to update our movie database every day in order to ensure that the users get reliable and up-to-date information.

For milestone 1, we have finished designing most of the user interface and have deployed a alpha version to Google App Engine. At this stage, users are able to register and login via using their email, Google and Facebook account. In addition, users are also able to see what are the current movies that are showing in our local cinema.

For the next milestone, users will be able to see more information about the movies that are currently showing in the cinema (time and location). We will also be completing the registration process so that a confirmation email will be sent to users when they register with us as well as sending them links to reset their password. We planned to finish the movie recommendation engine by milestone 2. We will leave user testing and other fixes that the peer teams suggest in the last stage.

## Project Log
Wei Kang has experienced with developing and deploying application to Google App Engine, as such he is able to provide the team guidance on how to kickstart the project and this allows the team to have more time to integrate more features into the project.

| Description | David | Wei Kang |
| --- | :---: | :---: |
| Liftoff Day 1 | 8 | 8 |
| Liftoff Day 2 | 8 | 8 |
| Project brainstorm and planning | 3 | 3 |
| Install IDE and tools | - | 2 |
| Design user interface | - | 23 |
| Explore Single Sign-On (SSO) platform | - | 2 |
| Implement Google Identity Toolkit | - | 24 |
| Explore TMDB movie database api | - | 2 |
| Preparation for milestone 1 | - | 1.5 |
| **Total:** | 19 | 73.5 |

We have finished 19 and 73.5 hours respectively towards the requirement.

## Mission
- [ ] Attend at least 12 hours of workshops
- [X] Use a web application with CRUD capabilities
- [ ] 4 additional extension milestones. (Still thinking...)
- [ ] Score a minimum of 2 stars on feedback given to other teams
- [ ] Must score a minimum of 3 stars on own peer-graded project

## Milestones
### Milestone 1
- [X] Create accounts and install IDE and tools
- [X] Design prototype (complete most of the prototype)
- [X] Implement Google Identity Toolkit (able to login and register)

### Milestone 2
- [ ] Implement Google Identity Toolkit (send email for verification and change password)
- [ ] Design recommendation engine
- [ ] Implement recommendation engine

### Milestone 3
- [ ] Conduct user testing
- [ ] Gather feedback on how to improve the user experience
- [ ] Improve performance

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
