# Shifty

An (unofficial) Android based Openshift client.

***Note: This application is a simple prototype developed for a college project. Please do not use this application with anything other than a test cluster***

## Getting Started

### Compatibility Matrix

Version `4.7.1` of the [fabric8io/kubernetes-client](https://github.com/fabric8io/kubernetes-client) was used for development

|                           | OpenShift  3.6.0 | OpenShift  3.7.0  | OpenShift  3.9.0  | OpenShift 3.10.0 | OpenShift 3.11.0 | OpenShift 4.1.0 | OpenShift 4.2.0 |
|---------------------------|------------------|-------------------|-------------------|------------------|------------------|-----------------|-----------------|
| openshift-client 4.7.1    | -                | -                 | ✓                 | ✓                | ✓                | ✓               | ✓               |

## Features

- Login/Authentication with Firebase Authentication
- CRUD functionality - list, create, delete and update user defined cluster models
- Swipe to delete/edit clusters
- View and create API resources

## Built With

- [AsynKio](https://github.com/CuriousNikhil/AsynKio) - Asynchronous networking
- [fabric8io/kubernetes-client](https://github.com/fabric8io/kubernetes-client) - Openshift client
- [Firebase](https://firebase.google.com/) - Backend storage and UI
- [Glide](https://github.com/bumptech/glide) - Image loading
- [MaterialDrawerKt](https://github.com/zsmb13/MaterialDrawerKt) - Navigation drawer

