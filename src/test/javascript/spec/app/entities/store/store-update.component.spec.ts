/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import StoreUpdateComponent from '@/entities/store/store-update.vue';
import StoreClass from '@/entities/store/store-update.component';
import StoreService from '@/entities/store/store.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Store Management Update Component', () => {
    let wrapper: Wrapper<StoreClass>;
    let comp: StoreClass;
    let storeServiceStub: SinonStubbedInstance<StoreService>;

    beforeEach(() => {
      storeServiceStub = sinon.createStubInstance<StoreService>(StoreService);

      wrapper = shallowMount<StoreClass>(StoreUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          storeService: () => storeServiceStub,
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.store = entity;
        storeServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(storeServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.store = entity;
        storeServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(storeServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundStore = { id: 123 };
        storeServiceStub.find.resolves(foundStore);
        storeServiceStub.retrieve.resolves([foundStore]);

        // WHEN
        comp.beforeRouteEnter({ params: { storeId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.store).toBe(foundStore);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
