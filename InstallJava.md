# Установка JDK

Для запуска Allure необходимо установить Java Development Kit (JDK) — комплект для разработки на Java. Ниже приведены инструкции по установке JDK на различных операционных системах.

## Windows

### Windows 7 и 8
1. Нажмите правой кнопкой мыши на ярлык "Компьютер" на рабочем столе и выберите "Свойства".
2. Найдите строку "Тип системы", чтобы определить разрядность операционной системы.
3. Скачайте соответствующий файл JDK:
   - Разрядность x64: [Ссылка](https://corretto.aws/downloads/latest/amazon-corretto-11-x64-windows-jdk.msi)
   - Разрядность x86: [Ссылка](https://corretto.aws/downloads/latest/amazon-corretto-11-x86-windows-jdk.msi)
4. Установите JDK, следуя инструкциям установщика.

### Windows 10
1. Нажмите правой кнопкой мыши на кнопку "Пуск" и выберите "Параметры".
2. Перейдите в раздел "Система" и затем на вкладку "О системе", чтобы найти разрядность системы.
3. Скачайте соответствующий файл JDK и установите его.

## MacOS

1. Скачайте файл JDK по [ссылке](https://corretto.aws/downloads/latest/amazon-corretto11-x64-macos-jdk.pkg).
2. Установите JDK, следуя инструкциям.

## Linux (дистрибутивы на основе Debian)

1. Откройте терминал и выполните команды:
   ```bash
   wget -O- https://apt.corretto.aws/corretto.key | sudo apt-key add -
   sudo add-apt-repository 'deb https://apt.corretto.aws stable main'
   sudo apt update
   sudo apt-get install -y java-11-amazon-corretto-jdk
   ```

## Настройка JDK

### Windows
Необходимо создать переменные окружения JAVA_HOME и Path для указания пути к JDK. Если установщик Amazon Corretto не создал их автоматически, вы можете сделать это самостоятельно.

### MacOS
```bash
export JAVA_HOME=/Library/Java/JavaVirtualMachines/amazon-corretto-11.jdk/Contents/Home
```

### Linux
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-amazon-corretto
```

## Проверка JDK

### Windows
1. Введите следующие команды в Cygwin терминале:
   ```bash
   echo $JAVA_HOME
   where java
   ```
2. Убедитесь, что переменные и JDK установлены правильно.

Теперь JDK установлен, настроен и готов к использованию.