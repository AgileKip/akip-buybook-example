/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';

import * as config from '@/shared/config/config';
import StoreComponent from '@/entities/store/store.vue';
import StoreClass from '@/entities/store/store.component';
import StoreService from '@/entities/store/store.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Store Management Component', () => {
    let wrapper: Wrapper<StoreClass>;
    let comp: StoreClass;
    let storeServiceStub: SinonStubbedInstance<StoreService>;

    beforeEach(() => {
      storeServiceStub = sinon.createStubInstance<StoreService>(StoreService);
      storeServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<StoreClass>(StoreComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          storeService: () => storeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      storeServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllStores();
      await comp.$nextTick();

      // THEN
      expect(storeServiceStub.retrieve.called).toBeTruthy();
      expect(comp.stores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      storeServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      comp.removeStore();
      await comp.$nextTick();

      // THEN
      expect(storeServiceStub.delete.called).toBeTruthy();
      expect(storeServiceStub.retrieve.callCount).toEqual(1);
    });
  });
});
