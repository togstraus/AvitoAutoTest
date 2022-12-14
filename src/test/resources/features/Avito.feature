#language: ru
@avito
#noinspection NonAsciiCharacters
Функционал: Поиск на авито

  Структура сценария: Найдем самые дорогие принтеры на авито
    Пусть открыт ресурс авито
    И в выпадающем списке категорий выбрана оргтехника
    И в поле поиска введено значение <техника>
    И активирован чекбокс только Новые
    Тогда кликнуть по выпадающему списку региона
    Тогда в поле регион введено значение <город>
    И нажата кнопка поиска объявления
    Тогда открылась страница результаты по запросу <техника>
    И в выпадающем списке сортировка выбрано значение <фильтр>
    И в консоль выведено значение названия и цены <количество> первых товаров

    Примеры:
      | техника | город           | количество | фильтр  |
      | принтер | Владивосток     | 3          | Дороже  |
      | сканер  | Санкт-Петербург | 5          | Дешевле |