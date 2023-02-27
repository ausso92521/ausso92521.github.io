from pymongo import MongoClient, cursor


class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self, user, pwd) -> None:
        """
        Initializes the class members client and database, based on input from class instantiation
        Sets each to default values if none are supplied.
        """
        self.client = MongoClient('mongodb://%s:%s@%s/?authSource=AAC' % (user, pwd, 'localhost:27017'))
        self.database = self.client['AAC']

    # Method to create new document into database collection
    def create(self, data: dict) -> bool:
        """
        Allows data to be inserted into database
        :param self: AnimalShelter() instance
        :param data: dictionary containing data to be inserted into database
        :return: boolean describing success of data insert to database
        """
        try:
            if len(data) > 1:
                return self.database.animals.insert_many(data).acknowledged  # more than one pair in dict - insert_many
            elif len(data) == 1:
                return self.database.animals.insert_one(data).acknowledged  # one pair in dict - insert_one
            else:
                raise Exception('data set is empty')  # no data inside dict - return false
        except Exception as e:
            print('Exception raised:', e)
            return False

    # Method to read document from database collection
    def read(self, data: dict, read_many: bool = False) -> cursor.Cursor:
        """
        Allows data to be read from the database
        :param read_many: Allows user to specify find_one() vs find()
        :param self: AnimalShelter() instance
        :param data: dictionary of data to match on lookup in database
        :return: cursor pointing to the data matched by the lookup in database
        """
        try:
            if data is not None:  # make sure we have data or raise exception
                return self.database.animals.find(data, {'_id': 0}) if read_many else \
                    self.database.animals.find_one(data, {'_id': 0})
        except Exception as e:
            print('Mongo exception:', e)
            return None

    # Method to update documents in database collection
    def update(self, lookup: dict, replacement: dict, update_many: bool = False) -> dict:
        """
        Allows user to look up and modify or replace a document in the database matching the lookup
        :param self: AnimalShelter() instance
        :param lookup: dictionary containing data to lookup in the database
        :param replacement: dictionary containing fields to replace in the matched lookup document in the database
        :param update_many: (optional) boolean allows user to specify replacement of one or many documents in db
        :return: dictionary (JSON) update operation response from the Mongo database
        """
        replacement = {'$set': replacement}
        try:
            if len(lookup) < 1:
                raise Exception('lookup cannot be blank')
            if len(replacement) < 1:
                raise Exception('replacement cannot be blank')
            return self.database.animals.update_many(lookup, replacement) if update_many \
                else self.database.animals.update_one(lookup, replacement)
        except Exception as e:
            print('Mongo Exception:', e)
            return None

    #  Method to delete documents in database collection
    def delete(self, lookup: dict, delete_many: bool = False) -> dict:
        """
        Allows user to delete a single document or many documents from the database matching the lookup
        :param self: AnimalShelter() instance
        :param lookup: dict used to lookup document on database
        :param delete_many: (optional) boolean allows user to specify replacement of one or many documents in database
        :return: dictionary (JSON) delete operation response from the Mongo database
        """
        try:
            if len(lookup) < 1:
                raise Exception('lookup dict must nor be empty')  # check if lookup is empty
            return self.database.animals.delete_many(lookup) if delete_many \
                else self.database.animals.delete_one(lookup)  # return correction operation (one/many)
        except Exception as e:
            print('Mongo Exception:', e)  # print exception
            return None
