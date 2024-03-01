#include <QtWidgets/QApplication>
#include"teste.h"
#include"gui.h"
int main(int argc, char *argv[])
{
    testDom();
    testRepo();testServ();
    QApplication a(argc, argv);
    Repo r{ "C:/Users/andre/source/repos/pr6/pr6/jocuri.txt" };
    JocValidator v;
    Service s{ r,v };
    gui g{ s };
    g.show();
    return a.exec();
}
