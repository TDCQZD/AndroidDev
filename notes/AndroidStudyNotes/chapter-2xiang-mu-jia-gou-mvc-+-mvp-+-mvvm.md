## MVC

MVC全名 Model View Controller

模型（model）-视图（view）-控制器（controller）

M是指业务模型

V是指用户界面

C则是控制器

其中 View 层其实就是程序的 UI 界面，用于向用户展示数据以及接收用户的输入

而 Model 层就是 JavaBean 实体类，用于保存实例数据

Controller 控制器用于更新 UI 界面和数据实例

## MVP

MVP是一种经典的模式

M代表Model

V代表View

P则是Presenter（Model和View之间的桥梁）

MVP模式的核心思想

把Activity中的UI逻辑抽象成View接口，把业务逻辑抽象成Presenter接口，Model类还是原来的Model类

**作用**

1.分离视图逻辑和业务逻辑，降低耦合

2.Activity只处理生命周期的任务，代码简洁

3.视图逻辑和业务逻辑抽象到了View和Presenter中，提高阅读性

4.Presenter被抽象成接口，可以有多种具体的实现

5.业务逻辑在Presenter中，避免后台线程引用Activity导致内存泄漏

## MVVM

MVVM模式包含三个部分

-Model代表基本的业务逻辑

-View显示内容

-ViewModel将前面两者联系在一起

