﻿using System.Collections.Generic;
using System.Collections.ObjectModel;

namespace CMOV_Stocks
{
    class CompanyListViewModel : ViewModelBase {
        ObservableCollection<Company> companyList = null;

        public ObservableCollection<Company> Companies {
            set { SetProperty(ref companyList, value); }
            get {
                if (companyList == null)
                    Initialize();
                return companyList;
            }
        }

        async private void Initialize() {
            if (CompanyDatabase.connected != 2) {
                try {
                    List<Company> listdb = await App.Database.GetItemsAsync();
                    Companies = new ObservableCollection<Company>(listdb);
                } catch (System.Exception e) {
                    List<Company> list = new List<Company>();
                    list.Add(new Company("AAPL", "Apple"));
                    list.Add(new Company("IBM", "IBM"));
                    list.Add(new Company("HPE", "Hewlett Packard"));
                    list.Add(new Company("MSFT", "Microsoft"));
                    list.Add(new Company("ORCL", "Oracle"));
                    list.Add(new Company("GOOGL", "Google"));
                    list.Add(new Company("FB", "Facebook"));
                    list.Add(new Company("TWTR", "Twitter"));
                    list.Add(new Company("INTC", "Intel"));
                    list.Add(new Company("AMD", "AMD"));
                    Companies = new ObservableCollection<Company>(list);
                }
            } 
        }
    }
}
