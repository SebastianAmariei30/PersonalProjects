/********************************************************************************
** Form generated from reading UI file 'pr6.ui'
**
** Created by: Qt User Interface Compiler version 6.5.0
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_PR6_H
#define UI_PR6_H

#include <QtCore/QVariant>
#include <QtWidgets/QApplication>
#include <QtWidgets/QMainWindow>
#include <QtWidgets/QMenuBar>
#include <QtWidgets/QStatusBar>
#include <QtWidgets/QToolBar>
#include <QtWidgets/QWidget>

QT_BEGIN_NAMESPACE

class Ui_pr6Class
{
public:
    QMenuBar *menuBar;
    QToolBar *mainToolBar;
    QWidget *centralWidget;
    QStatusBar *statusBar;

    void setupUi(QMainWindow *pr6Class)
    {
        if (pr6Class->objectName().isEmpty())
            pr6Class->setObjectName("pr6Class");
        pr6Class->resize(600, 400);
        menuBar = new QMenuBar(pr6Class);
        menuBar->setObjectName("menuBar");
        pr6Class->setMenuBar(menuBar);
        mainToolBar = new QToolBar(pr6Class);
        mainToolBar->setObjectName("mainToolBar");
        pr6Class->addToolBar(mainToolBar);
        centralWidget = new QWidget(pr6Class);
        centralWidget->setObjectName("centralWidget");
        pr6Class->setCentralWidget(centralWidget);
        statusBar = new QStatusBar(pr6Class);
        statusBar->setObjectName("statusBar");
        pr6Class->setStatusBar(statusBar);

        retranslateUi(pr6Class);

        QMetaObject::connectSlotsByName(pr6Class);
    } // setupUi

    void retranslateUi(QMainWindow *pr6Class)
    {
        pr6Class->setWindowTitle(QCoreApplication::translate("pr6Class", "pr6", nullptr));
    } // retranslateUi

};

namespace Ui {
    class pr6Class: public Ui_pr6Class {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_PR6_H
