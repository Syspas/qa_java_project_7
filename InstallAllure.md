# Установка Allure

Для того чтобы просматривать отчеты, необходимо установить следующие компоненты:

- Allure Framework: фреймворк для создания отчетов;
- allure-pytest: библиотека для работы с Pytest;
- JDK: среда выполнения Java, необходимая для работы с Allure.

## Установка Allure

### Windows

Для Windows необходимо скачать ZIP-архив с последней версией [Allure с официального сайта](https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/).

К примеру скачиваем версию [Allure 2.29.0](https://repo.maven.apache.org/maven2/io/qameta/allure/allure-commandline/2.29.0/allure-commandline-2.29.0.zip).
Распакуйте архив и добавьте путь к папке bin в переменную окружения Path.
Распаковываем архив allure-commandline-2.29.0.zip суда:
``` 
D:\APP\allure-commandline-2.29.0\allure-2.29.0\bin
```

Для доваления в Path Windows изпользуя команда.
``` shell
setx Path "%Path%;D:\APP\allure-commandline-2.29.0\allure-2.29.0\bin"
``` 

Для запуска Allure на Windows также потребуется установить среду выполнения Java (JRE или JDK).

После установки проверьте версию Allure, выполните команду в консоли.
``` shell
allure --version
```
Если версия отображается корректно, значит установка прошла успешно.

### MacOS

Для MacOS установку можно выполнить автоматически через менеджер пакетов Homebrew. Если у вас его нет, сначала выполните следующую команду в терминале для установки Homebrew:
``` shell
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

После установки Homebrew, выполните следующую команду для установки Allure:
``` shell
brew install allure
```


### Linux

На Linux установку Allure можно выполнить следующими командами в консоли:
``` shell
sudo apt-add-repository ppa:qameta/allure
sudo apt-get update
sudo apt-get install allure
```

## Дополнительная информация

- Allure Framework (https://docs.qameta.io/allure/)
- allure-pytest (https://pypi.org/project/allure-pytest/)
- Java Development Kit (JDK) (https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)


