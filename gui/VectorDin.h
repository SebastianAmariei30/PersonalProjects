#pragma once
#include "Activitate.h"

typedef Activitate Element;
template <typename ElementT>
class IteratorVectorT;

template <typename ElementT>
class VectorDinamicT {
public:

	/*
	Constructor default
	*/
	VectorDinamicT();

	/*
	Constructor de copiere
	*/
	VectorDinamicT(const VectorDinamicT& ot);

	/*
	Destructor
	*/
	~VectorDinamicT();

	/*
	Operator assignment
	*/
	VectorDinamicT& operator=(const VectorDinamicT& ot);

	/*
	Move constructor
	*/
	VectorDinamicT(VectorDinamicT&& ot);

	/*
	Move assignment
	*/
	VectorDinamicT& operator=(VectorDinamicT&& ot);

	void add(const ElementT& el);
	ElementT& get(int poz) const;
	void set(int poz, const ElementT& el);
	void erase(int poz);
	int size() const noexcept;

	friend class IteratorVectorT<ElementT>;
	IteratorVectorT<ElementT> begin();
	IteratorVectorT<ElementT> end();

private:
	int lg;
	int cap;
	ElementT* elems;

	void ensureCapacity();
};

template<typename ElementT>
VectorDinamicT<ElementT>::VectorDinamicT() :elems{ new Element[5] }, cap{ 5 }, lg{ 0 } {}
/*
Constructor de copiere
*/

template<typename ElementT>
VectorDinamicT<ElementT>::VectorDinamicT(const VectorDinamicT<ElementT>& ot) {
	elems = new ElementT[ot.cap];
	for (int i = 0;i < ot.lg;i++)
		elems[i] = ot.elems[i];
	lg = ot.lg;
	cap = ot.cap;
}

/*
Operator assignment
*/
template<typename ElementT>
VectorDinamicT<ElementT>& VectorDinamicT<ElementT>::operator=(const VectorDinamicT<ElementT>& ot) {
	if (this == &ot)
		return *this;
	delete[] elems;
	elems = new ElementT[ot.cap];
	for (int i = 0;i < ot.lg;i++)
		elems[i] = ot.elems[i];
	lg = ot.lg;
	cap = ot.cap;
	return *this;
}

/*
Destructor
*/

template<typename ElementT>
VectorDinamicT<ElementT>::~VectorDinamicT() {
	delete[] elems;
}

/*
Move constructor
*/

template<typename ElementT>
VectorDinamicT<ElementT>::VectorDinamicT(VectorDinamicT&& ot) {
	elems = ot.elems;
	lg = ot.lg;
	cap = ot.cap;

	ot.elems = nullptr;
	ot.lg = 0;
	ot.cap = 0;
}

/*
Move assignment
*/
template<typename ElementT>
VectorDinamicT<ElementT>& VectorDinamicT<ElementT>::operator=(VectorDinamicT<ElementT>&& ot) {

	delete[] elems;
	elems = ot.elems;
	lg = ot.lg;
	cap = ot.cap;

	ot.elems = nullptr;
	ot.lg = 0;
	ot.cap = 0;
	return *this;
}

template<typename ElementT>
void VectorDinamicT<ElementT>::add(const ElementT& el) {
	ensureCapacity();
	elems[lg++] = el;
}

template<typename ElementT>
ElementT& VectorDinamicT<ElementT>::get(int poz) const {
	return elems[poz];
}

template<typename ElementT>
void VectorDinamicT<ElementT>::set(int poz, const ElementT& el) {
	elems[poz] = el;
}
template<typename ElementT>
void VectorDinamicT<ElementT>::erase(int poz) {
	if (poz == lg-1)
	{
		lg--;
		return;
	}
	for (int i = 0;i < lg-1;i++)
	{
		if (i == poz)
		{
			for (int j = i;j < lg;j++)
				elems[j] = elems[j + 1];
			lg--;
			return;
		}
	}
}
template<typename ElementT>
int VectorDinamicT<ElementT>::size() const noexcept {
	return lg;
}

template<typename ElementT>
void VectorDinamicT<ElementT>::ensureCapacity() {
	if (lg < cap)
		return;
	cap = cap * 2;
	ElementT* aux = new ElementT[cap];
	for (int i = 0;i < lg;i++)
		aux[i] = elems[i];
	delete[] elems;
	elems = aux;
}

template<typename ElementT>
IteratorVectorT<ElementT> VectorDinamicT<ElementT>::begin()
{
	return IteratorVectorT<ElementT>(*this);
}

template<typename ElementT>
IteratorVectorT<ElementT> VectorDinamicT<ElementT>::end()
{
	return IteratorVectorT<ElementT>(*this, lg);
}

template<typename ElementT>
class IteratorVectorT {
private:
	const VectorDinamicT<ElementT>& v;
	int poz = 0;
public:
	IteratorVectorT(const VectorDinamicT<ElementT>& v) noexcept;
	IteratorVectorT(const VectorDinamicT<ElementT>& v, int poz) noexcept;
	bool valid()const;
	ElementT& element() const;
	void next() noexcept;
	ElementT& operator*();
	IteratorVectorT& operator++();
	bool operator==(const IteratorVectorT& ot) noexcept;
	bool operator!=(const IteratorVectorT& ot) noexcept;
};

template<typename ElementT>
IteratorVectorT<ElementT>::IteratorVectorT(const VectorDinamicT<ElementT>& v) noexcept: v{ v } {}

template<typename ElementT>
IteratorVectorT<ElementT>::IteratorVectorT(const VectorDinamicT<ElementT>& v, int poz) noexcept: v{ v },poz{ poz } {}

template<typename ElementT>
bool IteratorVectorT<ElementT>::valid() const {
	return poz < v.lg;
}

template<typename ElementT>
ElementT& IteratorVectorT<ElementT>::element() const {
	return v.elems[poz];
}

template<typename ElementT>
void IteratorVectorT<ElementT>::next() noexcept {
	poz++;
}

template<typename ElementT>
ElementT& IteratorVectorT<ElementT>::operator*(){
	return element();
}

template<typename ElementT>
IteratorVectorT<ElementT>& IteratorVectorT<ElementT>::operator++() {
	next();
	return *this;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::operator==(const IteratorVectorT<ElementT>& ot) noexcept {
	return poz == ot.poz;
}

template<typename ElementT>
bool IteratorVectorT<ElementT>::operator!=(const IteratorVectorT<ElementT>& ot) noexcept {
	return !(*this == ot);
}