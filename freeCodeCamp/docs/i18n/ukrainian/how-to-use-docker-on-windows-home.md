# Як використовувати Docker на Windows Home

Є декілька підводних каменів, яких треба уникнути, коли налаштовуєте Docker на Windows Home. По-перше, ви маєте використовувати [Docker Toolbox](https://docs.docker.com/toolbox/toolbox_install_windows/) як адміністратор. На жаль, Windows Home не підтримує Docker для робочого столу Windows, тому замість нього необхідно використовувати панель інструментів. Використайте роль адміністратора, оскільки при встановленні використовуються символічні посилання, які інакше створити неможливо.

Після встановлення панелі інструментів запустіть термінал Docker Quickstart від імені адміністратора. Це створить `default` віртуальну машину, якщо вона ще не створена. Одразу після цього закрийте термінал та відкрийте VirtualBox (знову як адміністратор). Ви можете побачити `default` машину. Цей сайт є доволі ресурсомістким, тому зупиніть віртуальну машину і підвищте налаштування наскільки ви зможете – зокрема, пам'ять. Було підтверджено, що він працює з 4 ГБ оперативної пам'яті.

Після того, як ви будете переконані, що Docker працює, скопіюйте репозиторій freeCodeCamp в середину `C:\Users`. Ці каталоги є загальними, надаючи Docker доступ до локальних каталогів, які йому необхідні під час встановлення.

Якщо ви бачите такі повідомлення

```shell
bash: change_volumes_owner.sh: No such file or directory
```

коли `npm run docker:init` це, ймовірно, злам.